package fr.challenge.filerouge_utopios.entity;

import java.io.Serializable;
import java.util.Objects;

public class ResultId implements Serializable {
    private Long userId;
    private Long gameId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResultId that = (ResultId) o;
        return Objects.equals(userId, that.userId) && Objects.equals(gameId, that.gameId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, gameId);
    }
}
