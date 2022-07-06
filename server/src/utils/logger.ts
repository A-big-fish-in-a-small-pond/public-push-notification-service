import winston from "winston";
import winstonDaily from "winston-daily-rotate-file";

const FILENAME = "logs/ppns";

const alignColorsAndTime = winston.format.combine(
    winston.format.colorize({
        all: true,
    }),
    winston.format.label({
        label: "[LOGGER]",
    }),
    winston.format.timestamp({
        format: "YYYY-MM-DD HH:MM:SS",
    }),
    winston.format.printf((info) => `[${info.timestamp}] [${info.level}] ${info.message}`)
);

const notalignColorsAndTime = winston.format.combine(
    winston.format.label({
        label: "[LOGGER]",
    }),
    winston.format.timestamp({
        format: "YYYY-MM-DD HH:MM:SS",
    }),
    winston.format.printf((info) => `[${info.timestamp}] [${info.level}] ${info.message}`)
);

export const logger = winston.createLogger({
    level: "debug",
    transports: [
        new winstonDaily({
            filename: FILENAME,
            zippedArchive: false,
            format: winston.format.combine(notalignColorsAndTime),
        }),

        new winston.transports.Console({
            format: winston.format.combine(winston.format.colorize(), alignColorsAndTime),
        }),
    ],
});
