INSTALACE A SPUŠTĚNÍ SYSTÉMU PRO SPRÁVU RESTAURACE

Postup instalace:
1. Pokud není v daném prostředí instalováno, nainstalovat Java Runtime Environment 1.6 ze souboru http://www.oracle.com/technetwork/java/javase/downloads/jre-6u30-download-1377142.html
2. Nainstalovat aplikační server WampServer 2.0i ze souboru http://sourceforge.net/projects/wampserver/files/WampServer%202/WampServer%202.0/WampServer2.0i.exe/download, případně zajistit přístup k jinému MySQL databázovému serveru
3. Vytvořit na databázovém serveru novou databázi db_rest_fel pomocí SQL skriptu /install/createDB.sql
4. Při jiném než implicitním nastavení databáze upravit parametry připojení v konfiguračním souboru /app/RestauraceFELServer/config/hibernate.cfg.xml

Spuštění programu:
1. Zajistit spuštění a přístupnost databázového serveru
2. Spustit server dávkovým souborem /app/setupServer.bat
3. Spustit klienta dávkovým souborem /app/setupClient.bat
4. Pro první přihlášení do systému použít údaje:

   Uživatelské jméno: NOVAK
   Heslo: 1234
   