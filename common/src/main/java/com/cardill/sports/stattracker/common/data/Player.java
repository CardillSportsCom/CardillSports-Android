package com.cardill.sports.stattracker.common.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Player implements Parcelable, Serializable {

    public String id;

    public String firstName;
    public String lastName;
    public int fieldGoalMade;
    public int fieldGoalMissed;
    public int onePointFieldGoalMade;
    public int twoPointFieldGoalMade;
    public int assists;
    public int rebounds;
    public int blocks;
    public int steals;
    public int turnovers;
    public int wins;
    public int gamesPlayed;
    private boolean isActive;
    private boolean shouldIgnoreStats;

    protected Player(Parcel in) {
        id = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        fieldGoalMade = in.readInt();
        fieldGoalMissed = in.readInt();
        onePointFieldGoalMade = in.readInt();
        twoPointFieldGoalMade = in.readInt();
        assists = in.readInt();
        rebounds = in.readInt();
        blocks = in.readInt();
        steals = in.readInt();
        turnovers = in.readInt();
        wins = in.readInt();
        gamesPlayed = in.readInt();
        isActive = in.readByte() != 0;
        shouldIgnoreStats = in.readByte() != 0;
    }

    public Player(String id, String firstName, String lastName, int fgm, int fga, int onePointFieldGoalMade, int twoPointFieldGoalMade, int assists,
                  int rebounds, int blocks, int steals, int turnovers) {

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fieldGoalMade = fgm;
        this.fieldGoalMissed = fga;
        this.onePointFieldGoalMade = onePointFieldGoalMade;
        this.twoPointFieldGoalMade = twoPointFieldGoalMade;
        this.assists = assists;
        this.rebounds = rebounds;
        this.blocks = blocks;
        this.steals = steals;
        this.turnovers = turnovers;
        this.isActive = true;
        this.shouldIgnoreStats = false;
    }

    public Player(String id, String firstName, String lastName, int fgm, int fga, int onePointFieldGoalMade, int twoPointFieldGoalMade, int assists,
                  int rebounds, int blocks, int steals, int turnovers, int wins, int gamesPlayed) {

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fieldGoalMade = fgm;
        this.fieldGoalMissed = fga;
        this.onePointFieldGoalMade = onePointFieldGoalMade;
        this.twoPointFieldGoalMade = twoPointFieldGoalMade;
        this.assists = assists;
        this.rebounds = rebounds;
        this.blocks = blocks;
        this.steals = steals;
        this.turnovers = turnovers;
        this.wins = wins;
        this.gamesPlayed = gamesPlayed;
        this.isActive = true;
        this.shouldIgnoreStats = false;

    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static final Creator<Player> CREATOR = new Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };

    public Player(
            String id,
            String firstName,
            String lastName,
            Integer fieldGoalMade,
            Integer fieldGoalMissed,
            Integer onePointFieldGoalMade,
            Integer twoPointFieldGoalMade,
            Integer assists,
            Integer rebounds,
            Integer blocks,
            Integer steals,
            Integer turnovers,
            Integer wins,
            Integer gamesPlayed,
            Boolean isActive,
            Boolean shouldIgnoreStats) {

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fieldGoalMade = fieldGoalMade;
        this.fieldGoalMissed = fieldGoalMissed;
        this.onePointFieldGoalMade = onePointFieldGoalMade;
        this.twoPointFieldGoalMade = twoPointFieldGoalMade;
        this.assists = assists;
        this.rebounds = rebounds;
        this.blocks = blocks;
        this.steals = steals;
        this.turnovers = turnovers;
        this.wins = wins;
        this.gamesPlayed = gamesPlayed;
        this.isActive = isActive;
        this.shouldIgnoreStats = shouldIgnoreStats;
    }

    public String id() {
        return id;
    }

    public String firstName() {
        return firstName;
    }

    public String lastName() {
        return lastName;
    }

    public int fieldGoalMade() {
        return fieldGoalMade;
    }

    public int fieldGoalMissed() {
        return fieldGoalMissed;
    }

    public int getOnePointFieldGoalMade() {return  onePointFieldGoalMade;}

    public int getTwoPointFieldGoalMade() {return  twoPointFieldGoalMade;}

    public int assists() {
        return assists;
    }

    public int rebounds() {
        return rebounds;
    }

    public int blocks() {
        return blocks;
    }

    public int steals() {
        return steals;
    }

    public int turnovers() {
        return turnovers;
    }

    public int wins() {
        return wins;
    }

    public int gamesPlayed() {
        return gamesPlayed;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean shouldIgnoreStats() {
        return shouldIgnoreStats;
    }

    public static Player create(String id, String firstName, String lastName) {
        return builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .fieldGoalMade(0)
                .fieldGoalMissed(0)
                .onePointFieldGoalMade(0)
                .twoPointFieldGoalMade(0)
                .assists(0)
                .rebounds(0)
                .blocks(0)
                .steals(0)
                .turnovers(0)
                .wins(0)
                .gamesPlayed(0)
                .isActive(true)
                .shouldIgnoreStats(false)
                .build();
    }

    public static Builder builder() {
        return new Builder()
                .fieldGoalMade(0)
                .fieldGoalMissed(0)
                .onePointFieldGoalMade(0)
                .twoPointFieldGoalMade(0)
                .assists(0)
                .rebounds(0)
                .blocks(0)
                .steals(0)
                .turnovers(0)
                .wins(0)
                .gamesPlayed(0)
                .isActive(false)
                .shouldIgnoreStats(false);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeInt(fieldGoalMade);
        dest.writeInt(fieldGoalMissed);
        dest.writeInt(onePointFieldGoalMade);
        dest.writeInt(twoPointFieldGoalMade);
        dest.writeInt(assists);
        dest.writeInt(rebounds);
        dest.writeInt(blocks);
        dest.writeInt(steals);
        dest.writeInt(turnovers);
        dest.writeInt(wins);
        dest.writeInt(gamesPlayed);
        dest.writeByte((byte) (isActive ? 1 : 0));
        dest.writeByte((byte) (shouldIgnoreStats ? 1 : 0));
    }

    public static final class Builder {
        private String id;
        private String firstName;
        private String lastName;
        private Integer fieldGoalMade;
        private Integer fieldGoalMissed;
        private Integer onePointFieldGoalMade;
        private Integer twoPointFieldGoalMade;
        private Integer assists;
        private Integer rebounds;
        private Integer blocks;
        private Integer steals;
        private Integer turnovers;
        private Integer wins;
        private Integer gamesPlayed;
        private Boolean isActive;
        private Boolean shouldIgnoreStats;

        Builder() {
        }
        private Builder(Player source) {
            this.id = source.id();
            this.firstName = source.firstName();
            this.lastName = source.lastName();
            this.fieldGoalMade = source.fieldGoalMade();
            this.fieldGoalMissed = source.fieldGoalMissed();
            this.onePointFieldGoalMade = source.getOnePointFieldGoalMade();
            this.twoPointFieldGoalMade = source.getTwoPointFieldGoalMade();
            this.assists = source.assists();
            this.rebounds = source.rebounds();
            this.blocks = source.blocks();
            this.steals = source.steals();
            this.turnovers = source.turnovers();
            this.wins = source.wins();
            this.gamesPlayed = source.gamesPlayed();
            this.isActive = source.isActive();
            this.shouldIgnoreStats = source.shouldIgnoreStats();
        }

        public Builder id(String id) {
            if (id == null) {
                throw new NullPointerException("Null id");
            }
            this.id = id;
            return this;
        }

        public Builder firstName(String firstName) {
            if (firstName == null) {
                throw new NullPointerException("Null firstName");
            }
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            if (lastName == null) {
                throw new NullPointerException("Null lastName");
            }
            this.lastName = lastName;
            return this;
        }

        public Builder fieldGoalMade(int fieldGoalMade) {
            this.fieldGoalMade = fieldGoalMade;
            return this;
        }

        public Builder fieldGoalMissed(int fieldGoalMissed) {
            this.fieldGoalMissed = fieldGoalMissed;
            return this;
        }

        public Builder onePointFieldGoalMade(int onePointFieldGoalMade) {
            this.onePointFieldGoalMade = onePointFieldGoalMade;
            return this;
        }

        public Builder twoPointFieldGoalMade(int twoPointFieldGoalMade) {
            this.twoPointFieldGoalMade = twoPointFieldGoalMade;
            return this;
        }

        public Builder assists(int assists) {
            this.assists = assists;
            return this;
        }

        public Builder rebounds(int rebounds) {
            this.rebounds = rebounds;
            return this;
        }

        public Builder blocks(int blocks) {
            this.blocks = blocks;
            return this;
        }

        public Builder steals(int steals) {
            this.steals = steals;
            return this;
        }

        public Builder turnovers(int turnovers) {
            this.turnovers = turnovers;
            return this;
        }

        public Builder wins(int wins) {
            this.wins = wins;
            return this;
        }

        public Builder gamesPlayed(int gamesPlayed) {
            this.gamesPlayed = gamesPlayed;
            return this;
        }

        public Builder isActive(boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        public Builder shouldIgnoreStats(boolean shouldIgnoreStats) {
            this.shouldIgnoreStats = shouldIgnoreStats;
            return this;
        }

        public Player build() {
            String missing = "";
            if (this.id == null) {
                missing += " id";
            }
            if (this.firstName == null) {
                missing += " firstName";
            }
            if (this.lastName == null) {
                missing += " lastName";
            }
            if (this.fieldGoalMade == null) {
                missing += " fieldGoalMade";
            }
            if (this.fieldGoalMissed == null) {
                missing += " fieldGoalMissed";
            }
            if (this.onePointFieldGoalMade == null) {
                missing += " onePointFieldGoalMade";
            }
            if (this.twoPointFieldGoalMade == null) {
                missing += " twoPointFieldGoalMade";
            }
            if (this.assists == null) {
                missing += " assists";
            }
            if (this.rebounds == null) {
                missing += " rebounds";
            }
            if (this.blocks == null) {
                missing += " blocks";
            }
            if (this.steals == null) {
                missing += " steals";
            }
            if (this.turnovers == null) {
                missing += " turnovers";
            }
            if (this.wins == null) {
                missing += " wins";
            }
            if (this.gamesPlayed == null) {
                missing += " gamesPlayed";
            }
            if (this.isActive == null) {
                missing += " isActive";
            }

            if (this.shouldIgnoreStats == null) {
                missing += " shouldIgnoreStats";
            }
            if (!missing.isEmpty()) {
                throw new IllegalStateException("Missing required properties:" + missing);
            }
            return new Player(
                    this.id,
                    this.firstName,
                    this.lastName,
                    this.fieldGoalMade,
                    this.fieldGoalMissed,
                    this.onePointFieldGoalMade,
                    this.twoPointFieldGoalMade,
                    this.assists,
                    this.rebounds,
                    this.blocks,
                    this.steals,
                    this.turnovers,
                    this.wins,
                    this.gamesPlayed,
                    this.isActive,
                    this.shouldIgnoreStats);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Player)) return false;
        Player player = (Player) obj;
        return this.id.equals(player.id);
    }
}
