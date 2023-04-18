package com.imatrix.files.upload.db.service;

import java.util.Optional;

public class testrun {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
System.out.println(getFileExt("testme-123.pdf").get() );
	}
	  public static Optional<String> getFileExt(String filename) {
		    return Optional.ofNullable(filename)
		      .filter(f -> f.contains("."))
		      .map(f -> f.substring(filename.lastIndexOf(".") + 1));
		}
}
