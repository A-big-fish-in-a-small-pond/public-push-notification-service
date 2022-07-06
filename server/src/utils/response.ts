import express from "express";
import httpError from "http-errors";

export const responseError = (req: express.Request, res: express.Response) => {
    res.status(404).json({
        code: 404,
        result: 404,
        msg: "Not Found : Service is Not Found",
    });
};

export const responseSend = (res: express.Response, result: any, data: any) => {
    res.status(200).json({
        code: 200,
        result: result,
        data: data,
    });
};
