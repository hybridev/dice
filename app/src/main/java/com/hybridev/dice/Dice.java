package com.hybridev.dice;

import java.util.ArrayList;
import java.util.Random;

public class Dice {

    DiceSurface firstSurface;
    DiceSurface secondSurface;
    DiceSurface thirdSurface;
    DiceSurface fourthSurface;
    DiceSurface fifthSurface;
    DiceSurface sixthSurface;
    ArrayList<DiceSurface> diceSurfaces;


    Dice()
    {
        firstSurface = new DiceSurface(1);
        secondSurface = new DiceSurface(2);
        thirdSurface = new DiceSurface(3);
        fourthSurface = new DiceSurface(4);
        fifthSurface = new DiceSurface(5);
        sixthSurface = new DiceSurface(6);

        diceSurfaces = new ArrayList<DiceSurface>();

        diceSurfaces.add(firstSurface);
        diceSurfaces.add(secondSurface);
        diceSurfaces.add(thirdSurface);
        diceSurfaces.add(fourthSurface);
        diceSurfaces.add(fifthSurface);
        diceSurfaces.add(sixthSurface);
    }

    Dice(DiceEnums.pieceType type)
    {
        switch (type){
            case Player:
                firstSurface = new DiceSurface(1);
                secondSurface = new DiceSurface(2);
                thirdSurface = new DiceSurface(3);
                fourthSurface = new DiceSurface(4);
                fifthSurface = new DiceSurface(5);
                sixthSurface = new DiceSurface(6);

                diceSurfaces = new ArrayList<DiceSurface>();

                diceSurfaces.add(firstSurface);
                diceSurfaces.add(secondSurface);
                diceSurfaces.add(thirdSurface);
                diceSurfaces.add(fourthSurface);
                diceSurfaces.add(fifthSurface);
                diceSurfaces.add(sixthSurface);
                break;
            case Enemy:
                firstSurface = new DiceSurface();
                secondSurface = new DiceSurface();
                thirdSurface = new DiceSurface();
                fourthSurface = new DiceSurface();
                fifthSurface = new DiceSurface();
                sixthSurface = new DiceSurface();

                diceSurfaces = new ArrayList<DiceSurface>();

                diceSurfaces.add(firstSurface);
                diceSurfaces.add(secondSurface);
                diceSurfaces.add(thirdSurface);
                diceSurfaces.add(fourthSurface);
                diceSurfaces.add(fifthSurface);
                diceSurfaces.add(sixthSurface);
                break;
            case Terrain:
                //주사위가 필요없을지도 모르기는한데 만들지 않을 방법이 있나?
                break;
            case Event:
                break;
        }
    }

    int roll()
    {
        int size = 0;
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        int r = random.nextInt(6) + 1;

        //Collections.shuffle(tempDice.diceSurfaces);
        //tempDice.diceSurfaces.get(0);

        switch (r)
        {
            case 1 :
                size = firstSurface.dicePips.size();
                break;
            case 2 :
                size = secondSurface.dicePips.size();
                break;
            case 3 :
                size = thirdSurface.dicePips.size();
                break;
            case 4 :
                size = fourthSurface.dicePips.size();
                break;
            case 5 :
                size = fifthSurface.dicePips.size();
                break;
            case 6 :
                size = sixthSurface.dicePips.size();
                break;
        }
        return size;
    }


    class DiceSurface
    {

        DicePip firstPip = null;
        DicePip secondPip = null;
        DicePip thirdPip = null;
        DicePip fourthPip = null;
        DicePip fifthPip = null;
        DicePip sixthPip = null;

        ArrayList<DicePip> dicePips;

        DiceSurface()
        {
            firstPip = new DicePip();
            dicePips = new ArrayList<DicePip>();
            dicePips.add(firstPip);
        }

        DiceSurface(int i)
        {
            dicePips = new ArrayList<DicePip>();
            switch (i){
                case 1 :
                    firstPip = new DicePip();
                    dicePips.add(firstPip);
                break;

                case 2 :
                    firstPip = new DicePip();
                    secondPip = new DicePip();
                    dicePips.add(firstPip);
                    dicePips.add(secondPip);
                break;

                case 3 :
                    firstPip = new DicePip();
                    secondPip = new DicePip();
                    thirdPip = new DicePip();
                    dicePips.add(firstPip);
                    dicePips.add(secondPip);
                    dicePips.add(thirdPip);
                break;

                case 4 :
                    firstPip = new DicePip();
                    secondPip = new DicePip();
                    thirdPip = new DicePip();
                    fourthPip = new DicePip();
                    dicePips.add(firstPip);
                    dicePips.add(secondPip);
                    dicePips.add(thirdPip);
                    dicePips.add(fourthPip);
                break;

                case 5 :
                    firstPip = new DicePip();
                    secondPip = new DicePip();
                    thirdPip = new DicePip();
                    fourthPip = new DicePip();
                    fifthPip = new DicePip();
                    dicePips.add(firstPip);
                    dicePips.add(secondPip);
                    dicePips.add(thirdPip);
                    dicePips.add(fourthPip);
                    dicePips.add(fifthPip);

                break;

                case 6 :
                    firstPip = new DicePip();
                    secondPip = new DicePip();
                    thirdPip = new DicePip();
                    fourthPip = new DicePip();
                    fifthPip = new DicePip();
                    sixthPip = new DicePip();
                    dicePips.add(firstPip);
                    dicePips.add(secondPip);
                    dicePips.add(thirdPip);
                    dicePips.add(fourthPip);
                    dicePips.add(fifthPip);
                    dicePips.add(sixthPip);
                break;
            }
        }
    }


    class DicePip
    {

        DicePip()
        {

        }
    }
}
