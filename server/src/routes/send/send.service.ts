import express from "express";
import { AUTHORIZATION } from "../../const/const";
import { session } from "../../session/memory";
import ERRORCODE, { CODE_AUTHORIZATION_NULL, CODE_CLIENT_NULL, CODE_CONTEXT_NULL, CODE_REQUEST_NULL, SUCCESS } from "../../utils/errorcode";
import { responseSend } from "../../utils/response";

export const sendService = (req: express.Request, res: express.Response, next: express.NextFunction) => {
    const authorization = req.headers.authorization;
    const { client, result } = req.body;

    if (AUTHORIZATION !== authorization) {
        return res.json(responseSend(res, { result: ERRORCODE.get(CODE_AUTHORIZATION_NULL) }, ""));
    }

    if (client == null) {
        return res.json(responseSend(res, { result: ERRORCODE.get(CODE_CLIENT_NULL) }, ""));
    }

    if (result == null) {
        return res.json(responseSend(res, { result: ERRORCODE.get(CODE_REQUEST_NULL) }, ""));
    }

    let context = session.getSession(client);

    if (context == null) {
        return res.json(responseSend(res, { result: ERRORCODE.get(CODE_CONTEXT_NULL) }, ""));
    }

    context
        .write(`${result}\n`)
        .then((result) => {
            return res.json(responseSend(res, { result: ERRORCODE.get(SUCCESS) }, JSON.stringify(result)));
        })
        .catch((err) => {
            return res.json(responseSend(res, { result: { resultCode: "999", resultMessage: err } }, ""));
        });
};
