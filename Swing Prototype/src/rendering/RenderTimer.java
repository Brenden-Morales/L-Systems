package rendering;

public class RenderTimer {

	private long CurrentTime = System.currentTimeMillis();
	private long PreviousTime = System.currentTimeMillis();
	public long DeltaTime = System.currentTimeMillis();
	
	private long FPSCurrentTime = System.currentTimeMillis();
	private long FPSPreviousTime = System.currentTimeMillis();
	
	public RenderTimer(){}
	{
		PreviousTime = System.currentTimeMillis();
	}
	
	public void Set(){
		PreviousTime = System.currentTimeMillis();
	}
	
	public long Tick(){
		CurrentTime = System.currentTimeMillis();
		DeltaTime = CurrentTime - PreviousTime;
		return DeltaTime;
	}
	
	public long FPSTick(){
		FPSCurrentTime = System.currentTimeMillis();
		long FPSDeltaTime = FPSCurrentTime - FPSPreviousTime;
		FPSPreviousTime = FPSCurrentTime;
		return FPSDeltaTime;
	}
	
}
