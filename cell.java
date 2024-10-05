public class cell {
    private boolean isShip;
    private boolean isHit;

    public cell(){
        this.isShip = false;
        this.isHit = false;
    }

    public String toString(){
        if(isHit && isShip){
            return "[ X ]";
        }
        else if(isShip)
            return "[ S ]";
        else if(isHit){
            return "[ * ]";
        }
        return "[   ]";
        
        
    }

    public void placeShip(){
        isShip = true;
    }

    public boolean isShip(){
        return isShip;
    }

}
