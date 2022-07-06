import { logger } from "../utils/logger";
import { Context } from "../vo/contextVO";
import { handlerVO } from "../vo/handlerVO";
import { SessionVO } from "../vo/sessionVO";

const handler: handlerVO = function (context: Context, session: SessionVO) {
    const ip = context.getId();

    context.onEvent("join").then((ip) => {
        logger.info(`user is joined : ${ip}`);
    });

    context.onEvent("error").then((err) => {
        logger.info(`user is logout : ${ip}`);
        session.deleteSession(ip);
    });

    context.onEvent("close").then((err) => {
        logger.info(`user is logout : ${ip}`);
        session.deleteSession(ip);
    });

    return context;
};

export default handler;
