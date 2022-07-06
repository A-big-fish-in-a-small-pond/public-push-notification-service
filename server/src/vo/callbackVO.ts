import { ResultVO } from "./resultVO";

export interface CallbackVO {
    (result: ResultVO): any;
}
