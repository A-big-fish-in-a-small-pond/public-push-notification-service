import net from "net";
import handler from "./context.handler";
import { logger } from "../utils/logger";
import { Context } from "../vo/contextVO";
import { userHandlerVO } from "../vo/handlerVO";
import { SessionVO } from "../vo/sessionVO";

const service = (req: userHandlerVO, session: SessionVO) => {
    const context = function (stream: net.Socket) {
        let process: Context = new Context(stream);
        process = handler(process, session);
        req(process);
    };

    const start = function (PORT: number) {
        return net.createServer(context).listen(PORT, "0.0.0.0", () => {
            logger.info("ppns service start. listening on 3000");
        });
    };

    return {
        start: start,
    };
};

export default service;
