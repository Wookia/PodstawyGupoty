package pszt;

public class LogKlauzula {
	private 
		Klauzula rodzic1;
		Klauzula rodzic2;
		Klauzula klauzula;
		int iteracja;
	public 
	LogKlauzula(Klauzula dziecko){
		this.rodzic1=null;
		this.rodzic2=null;
		this.klauzula=dziecko;
		this.iteracja=0;
	}
		LogKlauzula(Klauzula rodzic1, Klauzula rodzic2, Klauzula dziecko, int iteracja){
		this.rodzic1=rodzic1;
		this.rodzic2=rodzic2;
		this.klauzula=dziecko;
		this.iteracja=iteracja;
	}
	Klauzula getRodzic1(){
		return rodzic1;
	}
	Klauzula getRodzic2(){
		return rodzic2;
	}
	Klauzula getDziecko(){
		return klauzula;
	}
	int getIter(){
		return iteracja;
	}
}