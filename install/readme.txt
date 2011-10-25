INSTALACE A SPUŠTĚNÍ SYSTÉMU PRO SPRÁVU RESTAURACE

Postup instalace:
1. Pokud není v daném prostředí instalováno, nainstalovat Java Runtime Environment 1.6 ze souboru /install/jre-6u17-windows-i586-s.exe
2. Nainstalovat aplikační server WampServer 2.0i ze souboru /install/WampServer2.0i.exe, případně zajistit přístup k jinému MySQL databázovému serveru
3. Vytvořit na databázovém serveru novou databázi db_rest_fel pomocí SQL skriptu /install/createDB.sql
4. Při jiném než implicitním nastavení databáze upravit parametry připojení v konfiguračním souboru /app/RestauraceFELServer/config/hibernate.cfg.xml

Spuštění programu:
1. Zajistit spuštění a přístupnost databázového serveru
2. Spustit server dávkovým souborem /app/setupServer.bat
3. Spustit klienta dávkovým souborem /app/setupClient.bat
4. Pro první přihlášení do systému použít údaje:

   Uživatelské jméno: NOVAK
   Heslo: 1234
   