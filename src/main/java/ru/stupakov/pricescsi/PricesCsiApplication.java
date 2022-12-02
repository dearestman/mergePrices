package ru.stupakov.pricescsi;

import lombok.RequiredArgsConstructor;
import ru.stupakov.pricescsi.services.AppService;

import java.io.IOException;

@RequiredArgsConstructor
public class PricesCsiApplication {



	public static void main(String[] args) throws IOException {
		AppService.startApp();
	}

}


