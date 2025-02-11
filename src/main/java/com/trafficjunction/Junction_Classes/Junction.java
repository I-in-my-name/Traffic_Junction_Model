import java.util.Map;

class Junction {

    private Map<String, Integer> vehicle_rate = new HashMap<>();

    public void Junction() {
		
    }

    /*
     * @param direction: 
     * @return: 
     */
    public Integer getDirectionInfo(String direction) {
        return directionInfo.get(direction);
    }
}