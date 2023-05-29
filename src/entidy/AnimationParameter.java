package entidy;

public class AnimationParameter {
	private byte row,coll;
	private byte FStart,FEnd; //frame start and end
	
	public AnimationParameter(byte row,byte col,byte start,byte end) {
		this.row=row;
		this.coll=col;
		this.FStart=start;
		this.FEnd=end;
	}

	public byte getFStart() {
		return FStart;
	}

	public byte getFEnd() {
		return FEnd;
	}
	
	
	
}
