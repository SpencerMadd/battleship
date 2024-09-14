public class cell {
    private boolean isShip;
    private boolean isHit;

    public cell(){
        this.isShip = false;
        this.isHit = false;
    }

    public String toString(){
        if(isHit)
            return "[ X ]";
        else if(isShip)
            return "[ S ]";
        return "[ * ]";
        
    }

}
