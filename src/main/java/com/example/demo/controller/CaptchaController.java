package com.example.demo.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

@Controller
public class CaptchaController {

	@Autowired
	private Producer captchaProducer;

	@GetMapping("/captcha.jpg")
	public void getCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setContentType("image/jpeg");

		String captchaText = captchaProducer.createText();
		request.getSession().setAttribute("captcha", captchaText);

		BufferedImage image = captchaProducer.createImage(captchaText);

		ServletOutputStream output = response.getOutputStream();
		ImageIO.write(image, "jpg", output);

		try {
			output.flush();
		} finally {
			output.close();
		}

	}

	@Bean
	public Producer captcha() {
		Properties properties = new Properties();
		properties.setProperty("kaptcha.image.width", "150");
		properties.setProperty("kaptcha.image.height", "50");
		properties.setProperty("kaptcha.textproducer.char.string", "0123456789");
		properties.setProperty("kaptcha.textproducer.char.length", "4");
		
		Config config = new Config(properties);
		DefaultKaptcha kaptcha = new DefaultKaptcha();
		kaptcha.setConfig(config);
		return kaptcha;
	}
}
