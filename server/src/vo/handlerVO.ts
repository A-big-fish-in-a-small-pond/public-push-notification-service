import { Context } from "./contextVO";
import { SessionVO } from "./sessionVO";

export interface handlerVO {
    (context: Context, session: SessionVO): Context;
}

export interface userHandlerVO {
    (context: Context): void;
}
