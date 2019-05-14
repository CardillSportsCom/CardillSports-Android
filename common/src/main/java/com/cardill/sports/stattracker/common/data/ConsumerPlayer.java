package com.cardill.sports.stattracker.common.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.text.NumberFormat;

public class ConsumerPlayer implements Parcelable, Serializable {

    public String id;

    public String firstName;
    public String lastName;
    public int fieldGoalMade;
    public int fieldGoalMissed;
    public int points;
    public int threePointersMade;
    public int assists;
    public int rebounds;
    public int blocks;
    public int steals;
    public int turnovers;
    public int wins;
    public int gamesPlayed;
    private boolean isActive;
    private boolean shouldIgnoreStats;

    protected ConsumerPlayer(Parcel in) {
        id = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        fieldGoalMade = in.readInt();
        fieldGoalMissed = in.readInt();
        points = in.readInt();
        threePointersMade = in.readInt();
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

    public ConsumerPlayer(String id, String firstName, String lastName, int fgm, int fga, int points,
                          int threePointersMade, int assists, int rebounds, int blocks, int steals, int turnovers) {

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fieldGoalMade = fgm;
        this.fieldGoalMissed = fga;
        this.points = points;
        this.threePointersMade = threePointersMade;
        this.assists = assists;
        this.rebounds = rebounds;
        this.blocks = blocks;
        this.steals = steals;
        this.turnovers = turnovers;
        this.isActive = true;
        this.shouldIgnoreStats = false;
    }

    public ConsumerPlayer(String id, String firstName, String lastName, int wins, int gamesPlayed,
                          int fgm, int fga, int points, int threePointersMade, int assists,
                          int rebounds, int blocks, int steals, int turnovers) {

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fieldGoalMade = fgm;
        this.fieldGoalMissed = fga;
        this.points = points;
        this.threePointersMade =  threePointersMade;
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

    public static final Creator<ConsumerPlayer> CREATOR = new Creator<ConsumerPlayer>() {
        @Override
        public ConsumerPlayer createFromParcel(Parcel in) {
            return new ConsumerPlayer(in);
        }

        @Override
        public ConsumerPlayer[] newArray(int size) {
            return new ConsumerPlayer[size];
        }
    };

    public ConsumerPlayer(
            String id,
            String firstName,
            String lastName,
            Integer fieldGoalMade,
            Integer fieldGoalMissed,
            Integer points,
            Integer threePointersMade,
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
        this.points = points;
        this.threePointersMade = threePointersMade;
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

    public int points() { return points; }

    public int getThreePointersMade() {return  threePointersMade;}

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

    public static ConsumerPlayer create(String id, String firstName, String lastName) {
        return builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .fieldGoalMade(0)
                .fieldGoalMissed(0)
                .points(0)
                .threePointersMade(0)
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
                .points(0)
                .threePointersMade(0)
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
        dest.writeInt(points);
        dest.writeInt(threePointersMade);
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

    public String getFieldGoaldPercentage(NumberFormat numberFormat) {
        float fg_percent = ((float) fieldGoalMade / fieldGoalMissed);
        return numberFormat.format(fg_percent);
    }

    public static final class Builder {
        private String id;
        private String firstName;
        private String lastName;
        private Integer fieldGoalMade;
        private Integer fieldGoalMissed;
        private Integer points;
        private Integer threePointersMade;
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
        private Builder(ConsumerPlayer source) {
            this.id = source.id();
            this.firstName = source.firstName();
            this.lastName = source.lastName();
            this.fieldGoalMade = source.fieldGoalMade();
            this.fieldGoalMissed = source.fieldGoalMissed();
            this.points = source.points;
            this.threePointersMade = source.getThreePointersMade();
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

        public Builder points(int points) {
            this.points = points;
            return this;
        }

        public Builder threePointersMade(int threePointersMade) {
            this.threePointersMade = threePointersMade;
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

        public ConsumerPlayer build() {
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
            if (this.points == null) {
                missing += " points";
            }
            if (this.threePointersMade == null) {
                missing += " threePointersMade";
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
            return new ConsumerPlayer(
                    this.id,
                    this.firstName,
                    this.lastName,
                    this.fieldGoalMade,
                    this.fieldGoalMissed,
                    this.points,
                    this.threePointersMade,
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
        if (!(obj instanceof ConsumerPlayer)) return false;
        ConsumerPlayer player = (ConsumerPlayer) obj;
        return this.id.equals(player.id);
    }
}
