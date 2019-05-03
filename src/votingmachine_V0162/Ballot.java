/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package votingmachine_V0162;

public class Ballot {
    private String title;
    private Candidate candidates[];

    public Ballot(String title, Candidate[] candidates) {
        this.title = title;
        this.candidates = candidates;
    }

    public Ballot makeClone() {
        Candidate candidates[] = new Candidate[this.candidates.length];
        for (int i = 0; i < this.candidates.length; i++) {
            candidates[i] = this.candidates[i].makeClone();
        }
        return new Ballot(this.title, candidates);
    }
    
    public String getTitle() {
        return title;
    }

    public Candidate[] getCandidates() {
        return candidates;
    }
    
    public Candidate selectedCandidate(boolean createClone) {
        for (Candidate candidate : this.candidates) {
            if (candidate.isSelected()) {
                if (createClone) {
                    return candidate.makeClone();
                } else {
                    return candidate;
                }    
            }
        }
        return null;    
    }
}
