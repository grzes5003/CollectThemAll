package com.jpo.demo;
import com.jpo.demo.dataClasses.GeneratorConstatnts;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class Tools {
    public static int max_height = GeneratorConstatnts.MAX_NUMBER_OF_HEIGH;
    public static int max_width = GeneratorConstatnts.MAX_NUMBER_OF_WIDTH;
    public static int max_platforms = GeneratorConstatnts.MAX_NUMBER_OF_PLATFORMS;
    public static int min_x_dist = GeneratorConstatnts.PLATFORM_GEN_MIN_X_DIST;
    public static int min_y_dist = GeneratorConstatnts.PLATFORM_GEN_MIN_Y_DIST;


    public static String generatePlatforms(int platforms_count){
        ArrayList<Platform> platforms = new ArrayList<Platform>();

        for(int i = 0; i < platforms_count; i++){
            Platform tmp_platform = randomPlatform();
            int rep = -1;
            while((rep = platforms.indexOf(tmp_platform))!= -1){ //if generated the same platform as before try again
                tmp_platform = randomPlatform(platforms.get(rep));
            };
            platforms.add(tmp_platform);
        }

        Platform invalid_platform = null;
        while ((invalid_platform = checkPlatforms(platforms))!= null){
            Platform tmp = randomPlatform(invalid_platform);
            int invalid_platform_idx = platforms.indexOf(invalid_platform);
            platforms.set(invalid_platform_idx, tmp);
        }

        return platformsToGrzesiekFrontNotion(platforms);
    }

    public static Platform randomPlatform(Platform exclude){
        Platform platform = new Platform();
        Random rd = new Random();

        do{
            platform.setX(1 + rd.nextInt(max_width));
            platform.setY(1 + rd.nextInt(max_height));
        }while (platform.equals(exclude));

        return platform;

    } //refac

    public static Platform randomPlatform(){
        Platform platform = new Platform();
        Random rd = new Random();

        platform.setX(rd.nextInt(max_width));
        platform.setY(rd.nextInt(max_height));

        return platform;

    } //refac

    public static Platform checkPlatforms(ArrayList<Platform> platforms){
        Platform flawed_platform = findPlatformDistandFromPlatform(platforms, min_x_dist, min_y_dist);
        if(flawed_platform != null)
            return flawed_platform;

        return null;
    } //refac

    public static int[] isArrayInArray(int[][] search_array, int[] pattern){
        for (int[] cur_array:search_array) {
            if(java.util.Arrays.equals(cur_array, pattern))
                return pattern;
        }
        return null;
    }

    public static int findArrayInArray(int[][] search_array, int[] pattern){
        for (int i = 0; i < search_array.length; i++) {
            if(java.util.Arrays.equals(search_array[i], pattern))
                return i;
        }
        return -1;
    }


    public static Platform findPlatformDistandFromPlatform(ArrayList<Platform> platforms, int x_distance, int y_distance) {
        /*Look for any platform which is closer than {x OR y}_distance [Grzegorz front-end block unit]
        from another platform
        @return: int[] found platform OR null if not found
        */

        for (Platform platform:platforms) {
            for (Platform rem_platform:platforms) {
                if(platform == rem_platform)
                    continue;   //skip if looks for itself
                if(Math.abs((platform.getX()) - rem_platform.getX()) < x_distance)
                    return rem_platform;
                if(Math.abs(platform.getY() - rem_platform.getY()) < y_distance)
                    return rem_platform;
            }
        }
        return null;
    } //refac

    public static String platformsToGrzesiekFrontNotion(ArrayList<Platform> platforms){
        StringBuilder g_notion = new StringBuilder("");

        for (Platform platform:platforms) {
            g_notion.append(platform.getX());
            g_notion.append(",");
            g_notion.append(platform.getY());
            g_notion.append("|");
        }

        g_notion.deleteCharAt(g_notion.lastIndexOf("|"));
        return g_notion.toString();
    } //refac
}
