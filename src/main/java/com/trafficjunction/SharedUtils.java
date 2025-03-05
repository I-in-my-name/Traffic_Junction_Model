package com.trafficjunction;

public class SharedUtils{
    public static boolean verifyLane(String[] lanetypes, boolean oneLightMode) {

        // idea to follow: L LF RF R AND there is maximum ONE LF and ONE RF
        // CANNOT HAVE LF AND M OR RF AND M but can have lf and rf. for onelane

        if (oneLightMode) {
            enum stage{leftAllowed, forwardAllowed, rightAllowed}
            stage currentStage = stage.leftAllowed;
            for (int i = 0; i < lanetypes.length; i++) {
                if (currentStage == stage.leftAllowed && !lanetypes[i].equals("L")){
                    if (lanetypes[i].equals("LF") || lanetypes[i].equals("F")) currentStage = stage.forwardAllowed;
                    else currentStage = stage.rightAllowed;
                }else if (currentStage == stage.forwardAllowed && !lanetypes[i].equals("F")){
                    if (lanetypes[i].equals("L") || lanetypes[i].equals("LF")) return false;
                    else currentStage = stage.rightAllowed;
                }else if (currentStage == stage.rightAllowed){
                    if(!lanetypes[i].equals("R")) return false;
                    else{
                        System.out.println(lanetypes[i]);
                    }
                }
                System.out.println("Stage: " + currentStage + " for loop " + i + " for character " + lanetypes[i]);
            }
        }
        return true;
    }
}
