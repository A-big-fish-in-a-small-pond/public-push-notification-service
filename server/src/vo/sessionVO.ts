import { Context } from "./contextVO";

export interface SessionVO {
    addSession(arg1: string, arg2: Context): void;
    deleteSession(arg1: string): void;
    getSession(arg1: string): Context | null;
    store: any;
    [x: string]: string | void | any;
}

export interface MemoryVO extends SessionVO {
    store: Map<string, Context>;
}
