package pieces;

import java.util.concurrent.BlockingDeque;

public enum Alliance {
    WHITE {
        @Override
        public int getDirection() {
            return -1;
        }
    },
    BLACK {
        @Override
        public int getDirection() {
            return 1;
        }
    };

    public abstract int getDirection();

    public boolean isWhite() {
        return this.equals(WHITE);
    }

    public boolean isBlack() {
        return this.equals(BLACK);
    }

}
