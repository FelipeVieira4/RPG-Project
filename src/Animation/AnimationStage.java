package Animation;

public class AnimationStage {
	private int startFrame,endFrame,posSheet;
	
	public AnimationStage(int s, int e, int p) {
		this.startFrame=s;
		this.endFrame=e;
		this.posSheet=p;
	}
	
	public int getStart() {
		return this.startFrame;
	}
	public int getEnd() {
		return this.endFrame;
	}
	public int getPosSheet() {
		return this.posSheet;
	}
	
}
