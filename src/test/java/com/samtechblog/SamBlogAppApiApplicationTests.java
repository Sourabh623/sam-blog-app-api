package com.samtechblog;

import com.samtechblog.models.Post;
import com.samtechblog.payloads.PostDto;
import com.samtechblog.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
class SamBlogAppApiApplicationTests {

	@Autowired
	UserRepository userRepository;

	@Autowired
	ModelMapper modelMapper;

	@Test
	void contextLoads() {
	}

	@Test
	public void UserRepo()
	{
		String className = this.userRepository.getClass().getName();
		String packageName = this.userRepository.getClass().getPackageName();
		System.out.println("class name "+className+"\n"+"package name "+packageName);
	}

	@Test
	public void modelMapperTest(){
		Post post = new Post();
		PostDto postDto = new PostDto();

		this.modelMapper.map(post, PostDto.class);
		Assertions.assertEquals(true, true);
		//Assertions.assertEquals(true, false);
	}

}
