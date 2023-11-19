package com.rules.engine.booking.com;


import com.mysql.cj.util.TimeUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;


/**
 * Assumption Auth will always be placed before capture event in happy case.
 * else it will fail
 */
public class CreditCardTransactionAuth {
    private static final String dtPattern = "yyyy-MM-dd hh:mm";

    public static void main(String[] args) throws ParseException {
        long convert = TimeUnit.HOURS.convert(1080, TimeUnit.MINUTES);
        System.out.println(convert);

        var op1 = new Operation(1, convertToDate("2021-01-01 00:00"), Action.AUTH, 1000, "24");
        var op2 = new Operation(2, convertToDate("2021-01-01 01:00"), Action.AUTH, 10,"24");
        var op3 = new Operation(3, convertToDate("2021-01-03 00:00"), Action.AUTH, 10,"24");
        var op4 = new Operation(4, convertToDate("2021-01-03 00:10"), Action.CAPTURE, 10, "100");
        var op5 = new Operation(5, convertToDate("2021-01-03 00:10"), Action.CAPTURE, 10,"3");
        var op6 = new Operation(6, convertToDate("2021-02-01 00:00"), Action.AUTH, 500,"240");
        var op7 = new Operation(7, convertToDate("2021-02-07 00:00"), Action.CAPTURE, 600,"6");

        var input = List.of(op1,op2,op3,op4,op5,op6,op7);

        verifyAndActionOnTransaction(1000, input);

    }

    private static void verifyAndActionOnTransaction(int limit, List<Operation> operations) {
        int currPendingLimit = limit;
        var authLookup = new HashMap<Long, Operation>();
        for(var currOperation : operations){
            if(currOperation.getId() == 6){
                int i = 0;
            }
            if(currOperation.getAction() == Action.AUTH){
                currPendingLimit += getNewLimit(authLookup, currOperation);
                if(currPendingLimit < currOperation.getAmount())
                    System.out.println("fail - insufficient fund, balance - " + currPendingLimit);
                else{
                    authLookup.put(currOperation.getId(), currOperation);
                    currPendingLimit -= currOperation.getAmount();
                    System.out.println("success, remaining balance is "+ currPendingLimit + " euro "+ currOperation.getAmount()+", hold is for hrs = "+ currOperation.getAuth());
                }
            }else{
                long authId = Long.parseLong(currOperation.getAuth());
                if(!authLookup.containsKey(authId)){
                    System.out.println("fail - there is no auth present for this capture event " + currOperation.getAuth());
                }else if(!isWithinExpiryWindow(authLookup.get(authId), currOperation)){
                    System.out.println("fail - previous auth window expired.");
                }else{
                    var authAmt = authLookup.get(authId).getAmount();
                    if(authAmt < currOperation.getAmount()){
                        System.out.println("fail - capture cannot exceed the auth amt - "+ authAmt);
                    }else {
                        //update the balance
                        authLookup.get(authId).setAmount(authAmt - currOperation.getAmount());
                        System.out.println("success, balance is - " + currPendingLimit);
                    }
                }

            }
        }
    }

    private static long getNewLimit(HashMap<Long, Operation> authLookup, Operation currOperation) {
        long currPendingLimit = 0;
        for (Operation lastAuthReq : new ArrayList<>(authLookup.values())) {
            if (!isWithinExpiryWindow(lastAuthReq, currOperation)) {
                currPendingLimit += lastAuthReq.getAmount();
                System.out.println("(auth with id = " + lastAuthReq.getId()
                        + " was expire after " + lastAuthReq.getAuth() + "hrs)");
                authLookup.remove(lastAuthReq.getId());
            }
        }
        return currPendingLimit;
    }

    private static boolean isWithinExpiryWindow(Operation existingAuth, Operation currOperation) {
        //we need to check if the time for the existingAuth has expired
        long expiryTime = Long.parseLong(existingAuth.getAuth());
        Date lastAuthDate = existingAuth.getTimeStamp();
        Date currAuthDate = currOperation.getTimeStamp();
        return expiryTime >= getDateDiff(lastAuthDate, currAuthDate, TimeUnit.HOURS);
    }

    private static long getDateDiff(Date lastAuthDate, Date currAuthDate, TimeUnit timeUnit) {
        long diff = currAuthDate.getTime() - lastAuthDate.getTime();
        return timeUnit.convert(diff, TimeUnit.MILLISECONDS);
    }


    static Date convertToDate(String date) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dtPattern);
        return simpleDateFormat.parse(date);
    }
}


class Operation {
    private long id;
    private Date timeStamp;

    private Action action;

    private long amount;

    private String auth;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public Operation(long id, Date timeStamp, Action action, long amount, String auth) {
        this.id = id;
        this.timeStamp = timeStamp;
        this.action = action;
        this.amount = amount;
        this.auth = auth;
    }
}

enum Action {
    AUTH,
    CAPTURE
}