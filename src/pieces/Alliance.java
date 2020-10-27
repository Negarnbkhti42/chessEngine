package pieces;

import player.BlackPlayer;
import player.Player;
import player.WhitePlayer;

import java.util.concurrent.BlockingDeque;

public enum Alliance {
    WHITE {
        @Override
        public int getDirection() {
            return -1;
        }

        @Override
        public Player choosePlayer(final WhitePlayer whitePlayer, final BlackPlayer blackPlayer) {
            return whitePlayer;
        }
    },
    BLACK {
        @Override
        public int getDirection() {
            return 1;
        }

        @Override
        public Player choosePlayer(final WhitePlayer whitePlayer, final BlackPlayer blackPlayer) {
            return blackPlayer;
        }
    };

    public abstract int getDirection();

    public boolean isWhite() {
        return this.equals(WHITE);
    }

    public boolean isBlack() {
        return this.equals(BLACK);
    }

    public abstract Player choosePlayer(final WhitePlayer whitePlayer, final BlackPlayer blackPlayer);
}
