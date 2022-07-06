import EventEmitter from "events";
import net, { AddressInfo } from "net";
import { CallbackVO } from "./callbackVO";
import { ResultVO } from "./resultVO";
import { AuthStateVO, ReadStateVO, StateVO, STATE_AUTH, STATE_READ } from "./stateVO";

export class Context extends EventEmitter {
    private stream: net.Socket;
    private id: string;
    private buffer = Buffer.alloc(0);
    private state: StateVO;
    private cb: CallbackVO | null;

    constructor(stream: net.Socket) {
        super();
        this.stream = stream;
        this.init();

        let check = this.stream.remoteAddress;

        if (this.address2id(check)) {
            this.id = check;
        } else {
            this.id = "";
        }

        this.state = new AuthStateVO();
        this.cb = null;
    }

    address2id(id: string | undefined): id is string {
        return <string>id !== undefined;
    }

    getId() {
        return this.id;
    }

    init() {
        let self = this;

        this.stream.on("error", this.emit.bind(this, "error"));
        this.stream.on("close", this.emit.bind(this, "close"));
        this.stream.on("readable", function () {
            self.read();
        });

        process.nextTick(self.connect.bind(self));
    }

    connect() {
        this.emit("join", this.getId());
    }

    onEvent(event: string): Promise<any> {
        let self = this;
        return new Promise((resolve) => {
            self.on(event, (data) => {
                return resolve(data);
            });
        });
    }

    read() {
        try {
            let currentBuffer: Buffer = this.stream.read();
            this.buffer = Buffer.concat([this.buffer, currentBuffer]);

            let buffer2str = this.buffer.toString();
            if (buffer2str.indexOf("\n") < 0) return;
            this.readVariable(buffer2str);
        } catch (err) {
            this.emit("error", err);
        }
    }

    readVariable(str: string) {
        this.buffer = Buffer.alloc(0);
        str = str.slice(0, str.length - 1);
        let result: ResultVO = JSON.parse(str);

        if (this.state.kind === STATE_AUTH) {
            this.emit(STATE_AUTH, result);
            this.state = new ReadStateVO();
            return;
        } else {
            let cb = this.cb;
            this.cb = null;
            if (cb !== null) {
                return cb(result);
            }

            this.emit(STATE_READ, result);
        }
    }

    write(str: string): Promise<ResultVO> {
        let self = this;
        return new Promise((resolve) => {
            self.stream.write(str);

            self.cb = (data: ResultVO) => {
                return resolve(data);
            };
        });
    }

    disconnect() {
        this.stream.end();
    }
}
