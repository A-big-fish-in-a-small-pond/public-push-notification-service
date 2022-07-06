export interface StateVO {
    kind: string;
}

export const STATE_AUTH = "auth";
export const STATE_READ = "read";

export class AuthStateVO implements StateVO {
    kind: string;

    constructor() {
        this.kind = STATE_AUTH;
    }
}

export class ReadStateVO implements StateVO {
    kind: string;

    constructor() {
        this.kind = STATE_READ;
    }
}
