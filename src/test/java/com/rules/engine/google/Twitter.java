package com.rules.engine.google;
import java.util.*;
class Twitter {

    @Override
    public String toString() {
        return super.toString();
    }

    public static void main(String[] args) {
        /**
         * Explanation
         * Twitter twitter = new Twitter();
         * twitter.postTweet(1, 5); // User 1 posts a new tweet (id = 5).
         * twitter.getNewsFeed(1);  // User 1's news feed should return a list with 1 tweet id -> [5]. return [5]
         * twitter.follow(1, 2);    // User 1 follows user 2.
         * twitter.postTweet(2, 6); // User 2 posts a new tweet (id = 6).
         * twitter.getNewsFeed(1);  // User 1's news feed should return a list with 2 tweet ids -> [6, 5]. Tweet id 6 should precede tweet id 5 because it is posted after tweet id 5.
         * twitter.unfollow(1, 2);  // User 1 unfollows user 2.
         * twitter.getNewsFeed(1);  // User 1's news feed should return a list with 1 tweet id -> [5], since user 1 is no longer following user 2.
         */


         Twitter twitter = new Twitter();
         twitter.postTweet(1, 5); // User 1 posts a new tweet (id = 5).
         twitter.getNewsFeed(1);  // User 1's news feed should return a list with 1 tweet id -> [5]. return [5]
         twitter.follow(1, 2);    // User 1 follows user 2.
         twitter.postTweet(2, 6); // User 2 posts a new tweet (id = 6).
         twitter.getNewsFeed(1);  // User 1's news feed should return a list with 2 tweet ids -> [6, 5]. Tweet id 6 should precede tweet id 5 because it is posted after tweet id 5.
         twitter.unfollow(1, 2);  // User 1 unfollows user 2.
         twitter.getNewsFeed(1);  // User 1's news feed should return a list with 1 tweet id -> [5], since user 1 is no longer following user 2.

        Date date = new Date();
        long time = date.getTime();
    }
    private Map<Integer, User> userMap = null;
    public Twitter() {
        userMap = new HashMap<>();
    }

    public void postTweet(int userId, int tweetId) {
        var user = userMap.get(userId);
        if(user == null) {
            user = new User(userId);
            userMap.put(userId, user);
        }
        user.postTweet(tweetId);
    }

    public List<Integer> getNewsFeed(int userId) {
        var user = userMap.get(userId);
        if(user == null) return null;
        var ans = new ArrayList<Integer>(10);
        var topKTweets = new PriorityQueue<Integer>();
        addPostToResult(userId, topKTweets);
        addTweets(user.tweets, topKTweets);
        ans.addAll(topKTweets);
        Collections.reverse(ans);
        return ans;
    }

    private void addPostToResult(int userId, PriorityQueue<Integer> topKTweets){
        var user = userMap.get(userId);
        for(int fId : user.following){
            var fu = userMap.get(fId);
            addTweets(fu.tweets, topKTweets);
        }
    }

    private void addTweets(PriorityQueue<Integer> tweets, PriorityQueue<Integer> topKTweets){
        for(int tid : tweets){
            topKTweets.add(tid);
            if(topKTweets.size() > 10)
                topKTweets.remove();
        }
    }

    public void follow(int followerId, int followeeId) {
        var user = userMap.get(followerId);
        if(user == null)
            return;
        var followingUser = userMap.get(followeeId);
        if(followingUser == null){
            var newUser = new User(followeeId);
            userMap.put(followeeId,newUser);
        }
        user.follow(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        var user = userMap.get(followerId);
        if(user == null)
            return;
        user.unfollow(followeeId);
    }


    public static class User{
        public int id;
        public Set<Integer> following = new HashSet<>();
        public PriorityQueue<Integer> tweets = new PriorityQueue<>();
        private static final int MAX_LIMIT = 10;
        public User(int id){
            this.id = id;
        }
        public  void follow(int followingId){
            following.add(followingId);
        }
        public void unfollow(int followingId){
            following.remove(followingId);
        }
        public void postTweet(int tweetId){
            tweets.add(tweetId);
            if(tweets.size() > MAX_LIMIT)
                tweets.remove();
        }
    }
}

/***

 Entity:
 - User
 - Following
 - Tweets

 UserMap <Integer, User> //user id and the user obj
 User
 Id
 List<Following>


 TweetMap <Integer, Heap<Tweet>> //user id and heap of tweets sorted desc by the tweet id
 Tweets = [5(u1),6(u2)]

 * Post Audit
 * whenever the

 * post
 * fetch the user and keep the last top 10 tweets only
 * follow
 * fetach the user and add the follower in the following list
 * fetch
 * fetch the user
 * iterate over its following
 * plus the users own tweets posted
 * push it in to on minHeap of maintaining size K = 10
 * finally return that by reversing store in the list






 */


/**
 * Your Twitter object will be instantiated and called as such:
 * Twitter obj = new Twitter();
 * obj.postTweet(userId,tweetId);
 * List<Integer> param_2 = obj.getNewsFeed(userId);
 * obj.follow(followerId,followeeId);
 * obj.unfollow(followerId,followeeId);
 */