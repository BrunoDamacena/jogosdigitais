/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package player;

/**
 *
 * @author bruno
 */
public class Player {
    private float skill;
    private int currency, bills, billboard, gigsAvailable;
    
    public Player() {
        this.currency = 1000;
        this.bills = 1000;
        this.skill = 1;
        this.billboard = 1000;
        gigsAvailable = getGigsAvailable();
    }
    
    public void updatePlayer(int[][] week) {
        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 7; j++) {
                switch (week[i][j]) {
                    case 2: //working
                        currency += 25;
                        break;
                    case 3: //training
                        skill += 1; 
                        break;
                    case 4: //rehearsal
                        skill += 0.5;
                        billboard--;
                        break;
                    case 5: //gig
                        currency += 10 + 100/billboard;
                        skill += 0.3;
                        billboard -= 5;
                        break;
                    default:
                        break;
                }
            }
        }
        currency = currency - bills;
        if (billboard <= 0) billboard = 1;
        System.out.println("Currency: " + currency + ", billboard: " + billboard + ", skill: "+ skill);
    }
    
    public boolean winCondition() {
        return this.billboard == 1;
    }
    
    public boolean lossCondition(int weeksPassed) {
        return this.skill < weeksPassed * 10 || this.currency < 0;
    }
    
    public boolean bankrupt() {
        return this.currency < 0;
    }
    
    public float getCurrency() {
        return this.currency;
    }
    
    public int getBillboard() {
        return this.billboard;
    }
    
    public int getGigsAvailable() {
        if(billboard > 900) return 2;
        if(billboard > 850) return 3;
        if(billboard > 800) return 4;
        if(billboard > 850) return 5;
        if(billboard > 700) return 6;
        if(billboard > 600) return 7;
        if(billboard > 500) return 8;
        if(billboard > 400) return 9;
        return 10;
    }
    
    
}
