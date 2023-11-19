package com.rules.engine.heaps;

import java.util.*;
import java.util.stream.Collectors;

public class MeetingRoomIII {

    /**
     *  0 = {int[2]@728} [6, 41]        0       41
     * 1 = {int[2]@729} [13, 31]        1       31 *
     * 2 = {int[2]@730} [22, 30]        2       30 *
     * 3 = {int[2]@731} [24, 34]        3       34
     * 4 = {int[2]@732} [25, 36]        2       40
     * 5 = {int[2]@733} [31, 46]        1       46
     * 6 = {int[2]@734} [32, 36]        3       49
     * 7 = {int[2]@735} [37, 46]
     * 8 = {int[2]@736} [48, 49]
     * 9 = {int[2]@737} [49, 50]
     * @param args
     */

    public static void main(String[] args) {
        int[][] meetings = {{48,49},{22,30},{13,31},{31,46},{37,46},{32,36},{25,36},{49,50},{24,34},{6,41}};
        int room = 4;
        MeetingRoomIII roomIII = new MeetingRoomIII();
        roomIII.mostBooked(room, meetings);
        var arr = new ArrayList<String>(List.of("JFK-NRT-JFK-KUL", "JFK-KUL-NRT-JFK"));
        Collections.sort(arr);
        System.out.println(arr);

    }
    static class Room{
        public int id, end;
        public Room(int id, int end){
            this.id = id;
            this.end = end;
        }
    }

    //TC: O(NLogN + O(MLogM) + O(M * NLogN)) = O(M * NLogN)
    public int mostBooked(int n, int[][] meetings) {
        int[] roomUsed = new int[n];
        //rooms available by end time
        var rooms = new PriorityQueue<Room>((r1, r2)-> {
            if(r1.end == r2.end)
                return r1.id - r2.id;
            else
                return r1.end - r2.end;
        });

        //create all the available rooms
        for(int i = 0; i < n; i++) rooms.add(new Room(i, 0));

        var earliestMeetings = new PriorityQueue<int[]>((m1, m2)-> m1[0] - m2[0]);
        for(int[] meeting : meetings) earliestMeetings.add(meeting);

        while(!earliestMeetings.isEmpty()){
            int[] currMeeting = earliestMeetings.poll();
            Room room = null;
            List<Room> freeRooms = new ArrayList<Room>();
            while(!rooms.isEmpty() && rooms.peek().end < currMeeting[0]){
                Room freeRoom = rooms.poll();
                freeRoom.end = 0;
                freeRooms.add(freeRoom);
            }
            rooms.addAll(freeRooms);
            room = rooms.poll();
            roomUsed[room.id]++;
            int duration = currMeeting[1] - currMeeting[0];
            int newEnd = room.end != 0 ? room.end + duration : currMeeting[1];
            rooms.add(new Room(room.id, newEnd));
        }

        return getRoomUsedMax(roomUsed);

    }

    private int getRoomUsedMax(int[] rooms){
        System.out.println(Arrays.toString(rooms));
        int id = 0, used = Integer.MIN_VALUE;
        for(int i = 0; i < rooms.length; i++) {
            if(used < rooms[i])
            {
                used = rooms[i];
                id = i;
            }
        }
        return id;
    }
}


/**

 n = 2, meetings = [[0,10],[1,5],[2,7],[3,4]]

 10      4     5     1
 [[0,10],[1,5],[2,7],[3,4]]

 R1. 0---------------10   10-----11



 R2  1----------5.  5--------------10



 lookup
 room0 - is free and total count 2

 19     8      2     5     2
 n = 3, meetings = [[1,20],[2,10],[3,5],[4,9],[6,8]]

 R1      1---------------------------20


 R2      2--------------10   10----12


 R3      3---------5     5-----------10


 Approach:
 * Sort the array based on the start time
 *


 */
