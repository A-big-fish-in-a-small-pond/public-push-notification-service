import service from "./context/context.service";
import { session } from "./session/memory";
import { logger } from "./utils/logger";
import { Context } from "./vo/contextVO";
import { ResultVO } from "./vo/resultVO";

import express from "express";
import cors from "cors";
import api from "./routes/api";
import { responseError } from "./utils/response";
import { AUTHORIZATION, PPNS_CONTEXT_PORT, PPNS_RESTAPI_PORT } from "./const/const";

//* ppns
const handler = (context: Context) => {
    context.onEvent("auth").then((result: ResultVO) => {
        let user = context.getId();
        logger.info(`${user} come in auth service`);

        if (result.auth == AUTHORIZATION) {
            logger.info(`${user} access auth service`);
            session.addSession(user, context);
        } else {
            logger.info(`${user} disaccess auth service`);
            context.disconnect();
            session.deleteSession(user);
        }
    });
};

service(handler, session).start(PPNS_CONTEXT_PORT);

//* ppns restapi
const app = express();
app.use(express.json());
app.use(express.urlencoded({ extended: true }));
app.use(cors());

app.use("/api", api);

app.use(responseError);
app.listen(PPNS_RESTAPI_PORT, () => {
    logger.info("ppns restapi service start. listening on 3001");
});
