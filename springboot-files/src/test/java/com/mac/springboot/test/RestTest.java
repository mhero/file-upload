package com.mac.springboot.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.mac.springboot.repository.DocumentRepository;

@SpringBootTest
@AutoConfigureMockMvc
class RestTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private DocumentRepository documentRepository;

	@BeforeEach
	public void deleteAllBeforeTests() throws Exception {
		documentRepository.deleteAll();
	}

	@Test
	void shouldReturnRepositoryIndex() throws Exception {

		mockMvc.perform(get("/document/status")).andDo(print()).andExpect(status().isOk());
	}

	@Test
	void shouldCreateEntity() throws Exception {

		MockMultipartFile file = new MockMultipartFile("file", "origin.txt", null, "bar".getBytes());

		mockMvc.perform(MockMvcRequestBuilders.multipart("/document/upload").file(file)).andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value("1")).andExpect(jsonPath("$.title").value("origin.txt"));
	}
}