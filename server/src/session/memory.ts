import { Context } from "../vo/contextVO";
import { MemoryVO, SessionVO } from "../vo/sessionVO";

function createMemorySession(): SessionVO {
    let session: MemoryVO = {} as MemoryVO;
    session.store = new Map();

    session.addSession = (sesionId: string, context: Context) => {
        session.store.set(sesionId, context);
    };

    session.deleteSession = (sessionId: string) => {
        if (session.store.get(sessionId)) {
            session.store.delete(sessionId);
        }
    };

    session.getSession = (sessionId: string) => {
        let context = session.store.get(sessionId);
        if (context) {
            return context;
        } else {
            return null;
        }
    };

    return session;
}

export const session: SessionVO = createMemorySession();
