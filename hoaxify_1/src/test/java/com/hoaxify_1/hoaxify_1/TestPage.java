package com.hoaxify_1.hoaxify_1;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.Data;
@Data
public class TestPage<T> implements Page<T>{
 long totalElements;
 int totalPages;
 int numbers;
 int numberOfelements;
 int size;
 boolean last;
 boolean first;
 boolean next;
 boolean previous;
 
 List<T> content;
@Override
public int getNumber() {
	// TODO Auto-generated method stub
	return 0;
}
@Override
public int getSize() {
	// TODO Auto-generated method stub
	return 0;
}
@Override
public int getNumberOfElements() {
	// TODO Auto-generated method stub
	return 0;
}
@Override
public List<T> getContent() {
	// TODO Auto-generated method stub
	return null;
}
@Override
public boolean hasContent() {
	// TODO Auto-generated method stub
	return false;
}
@Override
public Sort getSort() {
	// TODO Auto-generated method stub
	return null;
}
@Override
public boolean isFirst() {
	// TODO Auto-generated method stub
	return false;
}
@Override
public boolean isLast() {
	// TODO Auto-generated method stub
	return false;
}
@Override
public boolean hasNext() {
	// TODO Auto-generated method stub
	return next;
}
@Override
public boolean hasPrevious() {
	// TODO Auto-generated method stub
	return previous;
}
@Override
public Pageable nextPageable() {
	// TODO Auto-generated method stub
	return null;
}
@Override
public Pageable previousPageable() {
	// TODO Auto-generated method stub
	return null;
}
@Override
public Iterator<T> iterator() {
	// TODO Auto-generated method stub
	return null;
}
@Override
public int getTotalPages() {
	// TODO Auto-generated method stub
	return 0;
}
@Override
public long getTotalElements() {
	// TODO Auto-generated method stub
	return 0;
}
@Override
public <U> Page<U> map(Function<? super T, ? extends U> converter) {
	// TODO Auto-generated method stub
	return null;
}
 
}
