package net.legendahlupa.com.cmdcooldown.Database;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@DatabaseTable(tableName = "CMDCooldown_data")
public class CooldownModel {
    public static final String COLUMN_Player_UUID = "Player_UUID";
    public static final String COLUMN_Command = "Command";
    public static final String COLUMN_CooldownTime = "CooldownTime";
    public static final String COLUMN_CooldownTimeStart = "CooldownTimeStart";

    @Getter
    @Setter
    @DatabaseField(columnName = COLUMN_Player_UUID, id = true, canBeNull = false)
    private UUID uuid;
    @DatabaseField(columnName = COLUMN_Command, canBeNull = false)
    private String command;
    @DatabaseField(columnName = COLUMN_CooldownTime, canBeNull = false)
    private int time;
    @DatabaseField(columnName = COLUMN_CooldownTimeStart, canBeNull = false)
    private long timestart;

    public CooldownModel(UUID uuid, String command, int time, long timestart) {
        this.uuid = uuid;
        this.command = command;
        this.time = time;
        this.timestart = timestart;
    }

    @Override
    public String toString() {
        return "DataModel{" +
                "uuid=" + uuid +
                ", command=" + command +
                ", time" + time+
                ", timestart" + timestart;

    }

}
