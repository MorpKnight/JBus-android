# JBus-android (FE)
JBus-android merupakan aplikasi android yang memungkinkan user untuk memesan tiket bus secara online. Aplikasi ini dibuat menggunakan bahasa pemrograman Java. Aplikasi ini dibuat untuk memenuhi tugas akhir mata kuliah Pemrograman Berorientasi Objek.

## Author
- [MorpKnight](https://github.com/MorpKnight)

## List fitur yang ada

Pada JBus-android ini banyak fitur yang ada, yaitu:
- Login
- Register
- Register sebagai `Renter` (Perusahaan yang menyediakan jasa bus)
- Sebagai `Renter` terdapat fitur
    - Menambahkan bus
    - Menambahkan station
    - Menambahkan jadwal pada bus
    - Menghapus jadwal atau bus
- Sebagai user biasa, dapat melihat order history

## Fitur yang gagal diimplementasi

Selain terdapat fitur yang sudah terimplemen, terdapat juga yang masih belum terimplementasi, seperti:
- Pada saat `Renter` menghapus jadwal bus, masih belum bisa mengembalikan uang yang terpakai untuk membeli tiket kepada user
- Masih belum bisa melakukan pembatalan terhadap tiket bus yang dibeli melalui `OrderHistoryActivity`
- Alur payment masih belum terimplementasi sepenuhnya, seperti kebanyakan pada *online shop* yang dimana penjual harus menyetujui terlebih dahulu pembelian dari user
- Belum adanya fitur tambahan seperti `Searc`, `Filter`, dan `Sort` pada `MainActivity` dan `OrderHistoryActivity`

## Installation
Silakan lihat pada page `Release` untuk mendapatkan file apk dari aplikasi ini.

## Cara pemakaian dengan penghubungan dengan Backend

Dari repository [ini](https://github.com/MorpKnight/JBus), atau langsung merajuk pada bagian [`Release`](https://github.com/MorpKnight/JBus/releases)

```bash
# file diganti dengan nama file yang didownload
java -jar file.jar
```