/** 000 : 성공 코드 */
export const SUCCESS = "000";

/** 010 : 파라미터 에러 및 사용자 에러코드 */
export const CODE_AUTHORIZATION_NULL = "011"; // 파라미터가 존재하지 않습니다.
export const CODE_CLIENT_NULL = "012"; //키가 존재하지 않습니다.
export const CODE_REQUEST_NULL = "013"; // 파라미터가 존재하지 않습니다.

/** 020 : 서버관리측면에서의 에러코드 */
export const CODE_CONTEXT_NULL = "021"; //  응답시간이 타임아웃되었습니다.

const ERRORCODE = new Map();
ERRORCODE.set(SUCCESS, { resultCode: SUCCESS, resultMessage: "성공" });

ERRORCODE.set(CODE_AUTHORIZATION_NULL, { resultCode: CODE_AUTHORIZATION_NULL, resultMessage: "접근 권한을 찾을 수 없습니다." });
ERRORCODE.set(CODE_CLIENT_NULL, { resultCode: CODE_CLIENT_NULL, resultMessage: "요청하신 클라이언트를 찾을 수 없습니다." });
ERRORCODE.set(CODE_REQUEST_NULL, { resultCode: CODE_REQUEST_NULL, resultMessage: "요청하신 메시지가 존재하지 않습니다." });
ERRORCODE.set(CODE_CONTEXT_NULL, { resultCode: CODE_CONTEXT_NULL, resultMessage: "접속하지 않는 클라이언트입니다." });

export default ERRORCODE;
