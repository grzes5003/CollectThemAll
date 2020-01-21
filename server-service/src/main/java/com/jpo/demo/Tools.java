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
        ArrayList<ArrayList> platforms = new ArrayList<ArrayList>();

        for(int i = 0; i < platforms_count; i++){
            ArrayList tmp_platform = randomPlatform();
            int rep = -1;
            while((rep = platforms.indexOf(tmp_platform))!= -1){ //if generated the same platform as before try again
                tmp_platform = randomPlatform(platforms.get(rep));
            };
            platforms.set(i, tmp_platform);
        }

        int[] invalid_platform = null;
        while ((invalid_platform = checkPlatforms(platforms))!= null){
            int[] tmp = randomPlatform(invalid_platform);
            int invalid_platform_idx = findArrayInArray(platforms, invalid_platform);
            platforms[invalid_platform_idx] = tmp;
        }

        return platformsToGrzesiekFrontNotion(platforms);
    }

    public static ArrayList randomPlatform(ArrayList exclude){
        ArrayList platform = new ArrayList();
        Random rd = new Random();

        do{
        platform.set(0, 1 + rd.nextInt(max_width));
        platform.set(1, 1 + rd.nextInt(max_height));
        }while (platform.equals(exclude));

        return platform;

    } //refac

    public static ArrayList randomPlatform(){
        ArrayList platform = new ArrayList();
        Random rd = new Random();
        platform.set(0, 1 + rd.nextInt(max_width));
        platform.set(1, 1 + rd.nextInt(max_height));

        return platform;

    } //refac

    public static int[] checkPlatforms(int[][] platforms){
        ArrayList flawed_platform = findPlatformDistandFromPlatform(platforms, min_x_dist, min_y_dist);
        if(flawed_platform != null)
            return flawed_platform;

        return null;
    }

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

    public static int[] findPlatformDistandFromPlatform(ArrayList<ArrayList<Integer>> platforms, int x_distance, int y_distance) {
        /*Look for any platform which is closer than {x OR y}_distance [Grzegorz front-end block unit]
        from another platform
        @return: int[] found platform OR null if not found
        */

        for (ArrayList<Integer> platform:platforms) {
            for (ArrayList<Integer> rem_platform:platforms) {
                if(platform == rem_platform)
                    continue;   //skip if looks for itself
                if(Math.abs((platform.get(0) - rem_platform.get(0)) < x_distance)
                    return rem_platform;
                if(Math.abs(platform[1] - rem_platform[1]) < y_distance)
                    return rem_platform;
            }
        }
        return null;
    }

    public static String platformsToGrzesiekFrontNotion(int[][] platforms){
        StringBuilder g_notion = new StringBuilder("");

        for (int[] platform:platforms) {
            g_notion.append(platform[0]);
            g_notion.append(",");
            g_notion.append(platform[1]);
            g_notion.append("|");
        }

        g_notion.deleteCharAt(g_notion.lastIndexOf("|"));
        return g_notion.toString();
    }
}
