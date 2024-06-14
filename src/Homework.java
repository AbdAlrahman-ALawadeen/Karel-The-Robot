import stanford.karel.SuperKarel;
public class Homework extends SuperKarel {
    private int cnt = 0;
    @Override
    public void run(){
        setBeepersInBag(1000);
        int width = Distance();
        int height = Distance();
        if((height == 1 && (width == 1 || width == 2)) || (width == 1 && height == 2)){
            if(height == 2){
                turnLeft();
                move();
            }
            else if(width == 2){
                move();
            }
            System.out.println("Can not Divided");
        }
        else if(height == 2 && width == 2){
            diagonal();
        }
        else if(height == 2 || height == 1){
            HeightOrWidth(height, width, true);
        }
        else if(width == 1 || width == 2){
            HeightOrWidth(height, width, false);
        }
        else if(height == width && height % 2 == 0){
            diagonal();
            StraightForward();
            turnLeft();
            diagonal();
            StraightForward();
        }
        else if(height % 2 == 0 && width % 2 == 0){
            StraightForwardEven(width / 2 - 1, false);
            ForwardLeft();
            StraightForwardEven(height / 2 - 1, false);
            ForwardLeft();
        }
        else if(height % 2 == 1 && width % 2 == 1){
            StraightForwardOdd(width / 2, height,false);
            StraightForwardOdd(height / 2, height,false);
        }
        else if(height % 2 == 1 && width % 2 == 0){
            StraightForwardEven(width / 2 - 1, false);
            turnRight();
            StraightForward();
            turnRight();
            StraightForwardOdd(height / 2, height,true);
        }
        else if(height % 2 == 0 && width % 2 == 1) {
            StraightForwardOdd(width / 2, height, false);
            StraightForwardEven(height / 2 - 1, true);
            ForwardLeft();
        }
        cnt = 0;
    }
    public void checkSafe(){
        while(true){
            if(frontIsBlocked()){
                turnLeft();
            }
            else{
                break;
            }
        }
    }
    public int Distance(){
        int count = 1;
        while(true){
            if(frontIsBlocked()){
                turnLeft();
                break;
            }
            count++;
            MoveAndCount();
        }
        return count;
    }
    public void CheckAndPut(){
        if(noBeepersPresent())
            putBeeper();
    }
    public void MoveAndCount(){
        move();
        cnt++;
        System.out.println(cnt);
    }
    public void diagonal(){
        while(true){
            if(frontIsBlocked() && leftIsBlocked()){
                CheckAndPut();
                break;
            }
            CheckAndPut();
            MoveAndCount();
            turnLeft();
            MoveAndCount();
            turnRight();
        }
        checkSafe();
    }
    public void ForwardLeft(){
        turnLeft();
        StraightForward();
        turnLeft();
    }
    public void HeightOrWidth(int height, int width, boolean isHeight) {
        int primary = isHeight ? height : width;
        int secondary = isHeight ? width : height;
        int temp = (secondary - 3) / 4;
        temp = (primary == 1 && secondary <= 6) ? 1 : temp;
        if ((isHeight && secondary <= 6 && primary != 1)|| (!isHeight && secondary <= 6 && primary != 1)) {
            ZigZagHeight(secondary, isHeight, isHeight ? 1 : 2);
            if (secondary == 6) {
                if (isHeight) {
                    LeftToRightOrTopToDown(primary);
                    RightToLeftOrDownToTop(secondary);
                } else {
                    RightToLeftOrDownToTop(width);
                    LeftToRightOrTopToDown(height);
                }
            } else if (secondary == 5) {
                if (isHeight) {
                    LeftToRightOrTopToDown(primary);
                } else {
                    RightToLeftOrDownToTop(width);
                }
            } else if (secondary == 3) {
                MoveAndCount();
            }
            return;
        }
        int full = 0;
        int counter = 0;
        boolean toggle = false;
        if (!isHeight) {
            turnLeft();
        }

        for (int i = 0; i < secondary; i++) {
            if (counter < temp) {
                if (!frontIsBlocked()) {
                    MoveAndCount();
                } else {
                    turnAround();
                }
                counter++;
            } else {
                full++;
                if (primary == 1) {
                    CheckAndPut();
                    if (!frontIsBlocked()) {
                        MoveAndCount();
                    } else {
                        if (isHeight) {
                            turnAround();
                        } else {
                            turnLeft();
                        }
                        break;
                    }
                } else {
                    if (!toggle) {
                        toggle = true;
                        if (isHeight) {
                            LeftToRightOrTopToDown(primary);
                        } else {
                            RightToLeftOrDownToTop(secondary);
                        }
                    } else {
                        toggle = false;
                        if (isHeight) {
                            RightToLeftOrDownToTop(secondary);
                        } else {
                            LeftToRightOrTopToDown(primary);
                        }
                    }
                }
                counter = full < 4 ? 0 : counter;
            }
        }
    }
    public void ZigZagHeight(int distance, boolean flag, int axis){
        boolean toggle = flag;
        int numberOfParts = distance < 4 ? 2 : 3;
        CheckAndPut();
        if(axis == 2){
            MoveAndCount();
            turnLeft();
            MoveAndCount();
        }
        else{
            turnLeft();
            MoveAndCount();
            turnRight();
            MoveAndCount();
        }
        for(int i = 0; i < numberOfParts; i++){
            if(!toggle){
                toggle = true;
                turnLeft();
                CheckAndPut();
                if((distance > 4) || i != numberOfParts - 1){
                    MoveAndCount();
                    turnRight();
                    MoveAndCount();
                }
            }
            else{
                toggle = false;
                turnRight();
                CheckAndPut();
                if(i != numberOfParts - 1 || distance > 4){
                    MoveAndCount();
                    turnLeft();
                    MoveAndCount();
                }
            }
        }
    }
    public void LeftToRightOrTopToDown(int height){
        turnLeft();
        CheckAndPut();
        MoveAndCount();
        turnRight();
        CheckAndPut();
        if(!frontIsBlocked()){
            MoveAndCount();
        }
        else{
            if(height == 1 || height == 2){
                turnAround();
            }
            else{
                turnRight();
                MoveAndCount();
                turnAround();
            }
        }
    }
    public void RightToLeftOrDownToTop(int width){
        turnRight();
        CheckAndPut();
        MoveAndCount();
        turnLeft();
        CheckAndPut();
        if(!frontIsBlocked()){
            MoveAndCount();
        }
        else{
            if(width == 1 || width == 2){
                turnAround();
            }
            else{
                turnLeft();
                MoveAndCount();
                turnLeft();
            }
        }
    }
    public void StraightForward(){
        while (!frontIsBlocked()){
            MoveAndCount();
        }
    }
    public void StraightForwardPut(){
        while (!frontIsBlocked()) {
            CheckAndPut();
            MoveAndCount();
        }
        CheckAndPut();
    }
    public void StraightForwardEven(int length, boolean even){
        for(int i = 0; i < length; i++){
            MoveAndCount();
        }
        if(!even){
            turnLeft();
            StraightForwardPut();
            turnRight();
            MoveAndCount();
            turnRight();
        }
        else{
            turnRight();
            StraightForwardPut();
            turnLeft();
            MoveAndCount();
            turnLeft();
        }
        StraightForwardPut();
    }
    public void StraightForwardOdd(int length, int height, boolean odd){
        for(int i = 0; i < length; i++){
            MoveAndCount();
        }
        if(!odd){
            turnLeft();
            StraightForwardPut();
            if(height % 2 == 0){
                turnRight();
                StraightForward();
                turnRight();
            }
            else{
                turnLeft();
                StraightForward();
                turnLeft();
            }
        }
        else{
            turnRight();
            StraightForwardPut();
            turnLeft();
            StraightForward();
            turnLeft();
        }
    }
    public void turnRight(){
        turnLeft();
        turnLeft();
        turnLeft();
    }
}
