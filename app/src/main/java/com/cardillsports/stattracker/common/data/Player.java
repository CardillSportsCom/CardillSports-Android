package com.cardillsports.stattracker.common.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.auto.value.AutoValue;

import java.io.Serializable;

public class Player implements Parcelable, Serializable {

    public String id;

    public String firstName;
    public String lastName;
    public int fieldGoalMade;
    public int fieldGoalMissed;
    public int assists;
    public int rebounds;
    public int blocks;
    public int steals;
    public int turnovers;

    protected Player(Parcel in) {
        id = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        fieldGoalMade = in.readInt();
        fieldGoalMissed = in.readInt();
        assists = in.readInt();
        rebounds = in.readInt();
        blocks = in.readInt();
        steals = in.readInt();
        turnovers = in.readInt();
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
            Integer assists,
            Integer rebounds,
            Integer blocks,
            Integer steals,
            Integer turnovers) {

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fieldGoalMade = fieldGoalMade;
        this.fieldGoalMissed = fieldGoalMissed;
        this.assists = assists;
        this.rebounds = rebounds;
        this.blocks = blocks;
        this.steals = steals;
        this.turnovers = turnovers;
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

    public static Player create(String id, String firstName, String lastName) {
        return builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .fieldGoalMade(0)
                .fieldGoalMissed(0)
                .assists(0)
                .rebounds(0)
                .blocks(0)
                .steals(0)
                .turnovers(0)
                .build();
    }

    static Player.Builder builder() {
        return new Builder()
                .fieldGoalMade(0)
                .fieldGoalMissed(0)
                .assists(0)
                .rebounds(0)
                .blocks(0)
                .steals(0)
                .turnovers(0);
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
        dest.writeInt(assists);
        dest.writeInt(rebounds);
        dest.writeInt(blocks);
        dest.writeInt(steals);
        dest.writeInt(turnovers);
    }

    public static final class Builder {
        private String id;
        private String firstName;
        private String lastName;
        private Integer fieldGoalMade;
        private Integer fieldGoalMissed;
        private Integer assists;
        private Integer rebounds;
        private Integer blocks;
        private Integer steals;
        private Integer turnovers;
        Builder() {
        }
        private Builder(Player source) {
            this.id = source.id();
            this.firstName = source.firstName();
            this.lastName = source.lastName();
            this.fieldGoalMade = source.fieldGoalMade();
            this.fieldGoalMissed = source.fieldGoalMissed();
            this.assists = source.assists();
            this.rebounds = source.rebounds();
            this.blocks = source.blocks();
            this.steals = source.steals();
            this.turnovers = source.turnovers();
        }

        public Player.Builder id(String id) {
            if (id == null) {
                throw new NullPointerException("Null id");
            }
            this.id = id;
            return this;
        }

        public Player.Builder firstName(String firstName) {
            if (firstName == null) {
                throw new NullPointerException("Null firstName");
            }
            this.firstName = firstName;
            return this;
        }

        public Player.Builder lastName(String lastName) {
            if (lastName == null) {
                throw new NullPointerException("Null lastName");
            }
            this.lastName = lastName;
            return this;
        }

        public Player.Builder fieldGoalMade(int fieldGoalMade) {
            this.fieldGoalMade = fieldGoalMade;
            return this;
        }

        public Player.Builder fieldGoalMissed(int fieldGoalMissed) {
            this.fieldGoalMissed = fieldGoalMissed;
            return this;
        }

        public Player.Builder assists(int assists) {
            this.assists = assists;
            return this;
        }

        public Player.Builder rebounds(int rebounds) {
            this.rebounds = rebounds;
            return this;
        }

        public Player.Builder blocks(int blocks) {
            this.blocks = blocks;
            return this;
        }

        public Player.Builder steals(int steals) {
            this.steals = steals;
            return this;
        }

        public Player.Builder turnovers(int turnovers) {
            this.turnovers = turnovers;
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
            if (!missing.isEmpty()) {
                throw new IllegalStateException("Missing required properties:" + missing);
            }
            return new Player(
                    this.id,
                    this.firstName,
                    this.lastName,
                    this.fieldGoalMade,
                    this.fieldGoalMissed,
                    this.assists,
                    this.rebounds,
                    this.blocks,
                    this.steals,
                    this.turnovers);
        }
    }

}
