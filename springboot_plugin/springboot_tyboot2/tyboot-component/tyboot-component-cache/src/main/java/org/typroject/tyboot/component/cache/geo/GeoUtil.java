package org.typroject.tyboot.component.cache.geo;

import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.typroject.tyboot.component.cache.Redis;

/**
 * Created by yaohelang on 2018/12/29.
 */
public class GeoUtil {





    public   GeoResults nearbyByGPS(String geoCacheKey,Double longitude , Double latitude, Double   radius, Long limit, int offset, int size) throws Exception
    {
        //保存用户当前位置
        Point point = new Point(longitude,latitude);
        //根据当前位置查询附近的人
        Distance distance                                           = new Distance(radius, RedisGeoCommands.DistanceUnit.METERS);
        Circle circle = new Circle(point,distance);
        RedisGeoCommands.GeoRadiusCommandArgs geoRadiusCommandArgs  = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs().limit(limit).sortAscending().includeCoordinates().includeDistance();
        GeoResults result             = Redis.getRedisTemplate().opsForGeo().geoRadius(geoCacheKey,circle,geoRadiusCommandArgs);
        GeoResults<RedisGeoCommands.GeoLocation> geoResults         = new GeoResults(result.getContent().subList(offset,(offset+size)>result.getContent().size()?result.getContent().size():(offset+size)),result.getAverageDistance());
        return geoResults;
    }




    public  <T> GeoResults nearby(String geoCacheKey,T member, Double   radius, Long limit, int offset, int size) throws Exception
    {
        //保存用户当前位置

        //根据当前位置查询附近的人
        Distance distance                                           = new Distance(radius, RedisGeoCommands.DistanceUnit.METERS);
        RedisGeoCommands.GeoRadiusCommandArgs geoRadiusCommandArgs  = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs().limit(limit).sortAscending().includeCoordinates().includeDistance();
        GeoResults result             = Redis.getRedisTemplate().opsForGeo().geoRadiusByMember(geoCacheKey,member,distance,geoRadiusCommandArgs);
        GeoResults<RedisGeoCommands.GeoLocation> geoResults         = new GeoResults(result.getContent().subList(offset,(offset+size)>result.getContent().size()?result.getContent().size():(offset+size)),result.getAverageDistance());
        return geoResults;
    }

}
