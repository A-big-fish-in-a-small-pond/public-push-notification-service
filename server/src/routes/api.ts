import express from "express";
import { pingService } from "./ping/ping.service";
import { sendService } from "./send/send.service";

const api = express.Router();

api.get("/ping", pingService);
api.post("/ping", pingService);
api.post("/send", sendService);

export default api;
