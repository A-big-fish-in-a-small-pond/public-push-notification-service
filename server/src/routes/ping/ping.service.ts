import express from "express";
import ERRORCODE, { SUCCESS } from "../../utils/errorcode";
import { responseSend } from "../../utils/response";

export const pingService = (req: express.Request, res: express.Response, next: express.NextFunction) => {
    return responseSend(res, { result: ERRORCODE.get(SUCCESS) }, "pong");
};
