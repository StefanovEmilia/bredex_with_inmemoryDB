package bredex.backendTest.rest.response;

import java.util.ArrayList;

public class PositionList extends Response{
	
	private ArrayList<String> urlList;

	public PositionList(ArrayList<String> urlList) {
		super();
		this.urlList = urlList;
	}

	public ArrayList<String> getUrlList() {
		return urlList;
	}

	public void setUrlList(ArrayList<String> urlList) {
		this.urlList = urlList;
	}

	@Override
	public String toString() {
		return "PositionList [urlList=" + urlList + ", toString()=" + super.toString() + "]";
	}

}
