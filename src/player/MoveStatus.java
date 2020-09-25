package player;

public enum MoveStatus {

    DONE {
        @Override
        public boolean isDone() {
            return true;
        }
    };

    public abstract boolean isDone();
}
