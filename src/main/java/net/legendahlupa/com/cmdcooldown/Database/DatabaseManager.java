package net.legendahlupa.com.cmdcooldown.Database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DatabaseManager {
    Dao<CooldownModel, UUID> DataModel;
    @Getter
    private Map<UUID, CooldownModel> cache = new ConcurrentHashMap<>();
    @Getter
    @Setter
    private HashMap<Player, String> waitInput = new HashMap<>();

    public DatabaseManager(ConnectionGenerator connectionGenerator) throws SQLException {

        ConnectionSource connection = connectionGenerator.getConnectionSource();

        this.DataModel = DaoManager.createDao(connection, CooldownModel.class);
        TableUtils.createTableIfNotExists(connection, CooldownModel.class);
        loadAllDataToCache();
    }


    private void loadAllDataToCache() throws SQLException {
        List<CooldownModel> allData = DataModel.queryForAll();
        for (CooldownModel model : allData) {
            cache.put(model.getUuid(), model);
        }
    }
    public boolean isCooldownExpired(UUID uuid) {
        CooldownModel model = getFromCache(uuid);
        long currentTime = System.currentTimeMillis();
        long elapsedTime = (currentTime - model.getTimestart()) / 1000; // Время, прошедшее с момента выполнения команды, в секундах
        return elapsedTime >= model.getTime();
    }
    public CooldownModel getFromCache(UUID id) {
        return cache.get(id);
    }

    public void putToCache(UUID uuid, CooldownModel cooldownModel) {
        cache.put(uuid, cooldownModel);

    }


    public void loadCacheToDataBase() throws SQLException {
        for (CooldownModel cooldownModel : cache.values()){
            DataModel.createOrUpdate(cooldownModel);
        }
    }

    public void removeFromCache(UUID uuid){
        cache.remove(uuid);
    }
    public void removeFromDataBase(CooldownModel cooldownModel) throws SQLException{
        DataModel.delete(cooldownModel);
    }

}

