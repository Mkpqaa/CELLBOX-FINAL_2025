-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 16-07-2025 a las 09:19:22
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `prueba3`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clientes`
--

CREATE TABLE `clientes` (
  `id` int(11) NOT NULL,
  `dni` varchar(20) DEFAULT NULL,
  `nombre` varchar(180) NOT NULL,
  `telefono` varchar(15) NOT NULL,
  `direccion` varchar(255) NOT NULL,
  `sueldo` double NOT NULL DEFAULT 0,
  `max_cuotas` int(11) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `clientes`
--

INSERT INTO `clientes` (`id`, `dni`, `nombre`, `telefono`, `direccion`, `sueldo`, `max_cuotas`) VALUES
(1, '75423906', 'Miguel Pacheco Quispe', '977669741', 'Lima - Perú', 0, 1),
(2, '65793926', 'Jean Carlos', '946 741 546', 'Av.Lima', 0, 1),
(3, '09666490', 'Maritza Lino', '988646262', 'San Juan de Lurigancho', 0, 1),
(4, '09554690', 'Miguel Marcos', '977979445', 'Lima, SJL', 0, 1),
(5, '76492641', 'Pamela Gonzales', '977985161', 'Lima, SJL', 0, 1),
(6, '09776974', 'Jean Carlos', '977764642', 'Lima', 0, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `compras`
--

CREATE TABLE `compras` (
  `id` int(11) NOT NULL,
  `producto_id` int(11) NOT NULL,
  `fecha_compra` date NOT NULL,
  `numero_factura` varchar(50) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `precio_cu` decimal(10,2) NOT NULL,
  `lote` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `compras`
--

INSERT INTO `compras` (`id`, `producto_id`, `fecha_compra`, `numero_factura`, `cantidad`, `precio_cu`, `lote`) VALUES
(1, 27, '2025-07-01', 'FAC-100', 10, 45.00, 1),
(2, 27, '2025-07-05', 'FAC-101', 5, 47.00, 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `config`
--

CREATE TABLE `config` (
  `id` int(11) NOT NULL,
  `ruc` int(15) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `telefono` int(11) NOT NULL,
  `direccion` text NOT NULL,
  `mensaje` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `config`
--

INSERT INTO `config` (`id`, `ruc`, `nombre`, `telefono`, `direccion`, `mensaje`) VALUES
(1, 209013490, 'CellBox SAC', 977669741, 'Lima - Perú', 'Gracias por su compra !');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalle`
--

CREATE TABLE `detalle` (
  `id` int(11) NOT NULL,
  `id_pro` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `precio` decimal(10,2) NOT NULL,
  `id_venta` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `detalle`
--

INSERT INTO `detalle` (`id`, `id_pro`, `cantidad`, `precio`, `id_venta`) VALUES
(4, 1, 5, 3000.00, 4),
(5, 3, 3, 600.00, 5),
(6, 3, 15, 600.00, 6),
(7, 3, 15, 600.00, 7),
(8, 3, 5, 600.00, 8),
(9, 3, 5, 600.00, 9),
(10, 2, 5, 500.00, 10),
(11, 4, 5, 1500.00, 11),
(12, 7, 5, 900.00, 12),
(13, 7, 5, 900.00, 13),
(14, 5, 3, 3000.00, 13),
(15, 3, 5, 600.00, 14),
(16, 10, 15, 1500.00, 15),
(17, 12, 1, 400.00, 16),
(18, 22, 12, 600.00, 17),
(19, 23, 8, 50.00, 18),
(20, 23, 2, 50.00, 19),
(21, 24, 13, 60.00, 19),
(22, 27, 5, 60.00, 20),
(23, 28, 5, 60.00, 21),
(24, 30, 4, 100.00, 22),
(25, 31, 10, 50.00, 23),
(26, 32, 3, 70.00, 23);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `kardex`
--

CREATE TABLE `kardex` (
  `id` int(11) NOT NULL,
  `fecha` date NOT NULL,
  `tipo` enum('ENTRADA','SALIDA') NOT NULL,
  `codigo_producto` varchar(50) NOT NULL,
  `lote` int(11) DEFAULT 0,
  `proveedor_id` int(11) DEFAULT NULL,
  `detalle` varchar(100) DEFAULT NULL,
  `e_cantidad` int(11) DEFAULT 0,
  `e_cu` decimal(10,2) DEFAULT 0.00,
  `e_ct` decimal(10,2) DEFAULT 0.00,
  `s_cantidad` int(11) DEFAULT 0,
  `s_cu` decimal(10,2) DEFAULT 0.00,
  `s_ct` decimal(10,2) DEFAULT 0.00,
  `d_cantidad` int(11) DEFAULT 0,
  `d_cu` decimal(10,2) DEFAULT 0.00,
  `d_ct` decimal(10,2) DEFAULT 0.00
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Volcado de datos para la tabla `kardex`
--

INSERT INTO `kardex` (`id`, `fecha`, `tipo`, `codigo_producto`, `lote`, `proveedor_id`, `detalle`, `e_cantidad`, `e_cu`, `e_ct`, `s_cantidad`, `s_cu`, `s_ct`, `d_cantidad`, `d_cu`, `d_ct`) VALUES
(1, '2025-07-01', 'ENTRADA', 'PROD001', 1, 1, 'Compra - Factura FAC-0014', 10, 100.00, 1000.00, 0, 0.00, 0.00, 10, 100.00, 1000.00),
(2, '2025-07-16', 'SALIDA', 'PROD001', 1, NULL, 'Venta - Boleta BOLETA-22', 0, 0.00, 0.00, 4, 100.00, 400.00, 6, 100.00, 600.00),
(3, '2025-07-01', 'ENTRADA', 'PRUEBA0156', 1, 1, 'Compra - Factura FAC-0015', 10, 50.00, 500.00, 0, 0.00, 0.00, 10, 50.00, 500.00),
(4, '2025-07-03', 'ENTRADA', 'PRUEBA0156', 2, 1, 'Compra - Factura FAC-0016', 5, 70.00, 350.00, 0, 0.00, 0.00, 5, 70.00, 350.00),
(5, '2025-07-16', 'SALIDA', 'PRUEBA0156', 1, NULL, 'Venta - Boleta BOLETA-23', 0, 0.00, 0.00, 10, 50.00, 500.00, 0, 50.00, 0.00),
(6, '2025-07-16', 'SALIDA', 'PRUEBA0156', 2, NULL, 'Venta - Boleta BOLETA-23', 0, 0.00, 0.00, 3, 70.00, 210.00, 2, 70.00, 140.00);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productos`
--

CREATE TABLE `productos` (
  `id` int(11) NOT NULL,
  `codigo` varchar(20) NOT NULL,
  `nombre` text NOT NULL,
  `marca` varchar(100) NOT NULL,
  `proveedor` int(11) NOT NULL,
  `stock` int(11) NOT NULL,
  `precio` decimal(10,2) NOT NULL,
  `precio_compra` decimal(10,2) DEFAULT NULL,
  `precio_venta` decimal(10,2) DEFAULT NULL,
  `lote` int(11) DEFAULT NULL,
  `imagen` longblob DEFAULT NULL,
  `fecha_compra` date DEFAULT NULL,
  `numero_factura` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `productos`
--

INSERT INTO `productos` (`id`, `codigo`, `nombre`, `marca`, `proveedor`, `stock`, `precio`, `precio_compra`, `precio_venta`, `lote`, `imagen`, `fecha_compra`, `numero_factura`) VALUES
(1, 'SMA56', 'Samsung A56', 'Samsung', 2, 20, 3000.00, NULL, NULL, NULL, NULL, NULL, NULL),
(2, 'SMA25', 'Samsung a25', 'Samsung', 2, 5, 500.00, NULL, NULL, NULL, NULL, NULL, NULL),
(3, 'SMA15', 'Samsung A15', 'Samsung', 10, 25, 600.00, NULL, NULL, NULL, NULL, NULL, NULL),
(4, 'X14', 'Xiaomi Redmi note 14', 'Xiaomi', 3, 15, 1500.00, NULL, NULL, NULL, NULL, NULL, NULL),
(5, 'IP14P', 'Apple IPhone 14 Pro', 'Apple', 10, 17, 3000.00, NULL, NULL, NULL, NULL, NULL, NULL),
(6, 'IP12', 'Apple Iphone 12 Pro', 'Apple', 5, 11, 2500.00, NULL, NULL, NULL, NULL, NULL, NULL),
(7, 'IP8', 'Iphone 8', 'Apple', 11, 5, 1500.00, NULL, NULL, NULL, NULL, NULL, NULL),
(8, 'IP14', 'IPhone 14', 'Apple', 7, 10, 2100.00, NULL, NULL, NULL, NULL, NULL, NULL),
(9, 'IP16', 'Iphone 16', 'Apple', 9, 25, 4000.00, NULL, NULL, NULL, NULL, NULL, NULL),
(10, 'IP15', 'Iphone 15', 'Apple', 9, 5, 1500.00, NULL, NULL, NULL, NULL, NULL, NULL),
(12, 'P9', 'Huawei p11', 'Huawei', 1, 9, 400.00, NULL, NULL, NULL, NULL, NULL, NULL),
(13, 'IP15PROMX', 'Iphone 15 pro max', 'Apple', 9, 20, 1500.00, 1500.00, 1500.00, 1, 0x6970686f6e652d31352d70726f2d6d6f64656c2d636f6d706172652e6a706722, '2025-07-11', 'FAC-0001'),
(14, 'IP15PROMX', 'Iphone 15 pro max', 'Apple', 11, 20, 1600.00, 1600.00, 1600.00, 2, 0x6970686f6e652d31352d70726f2d6d6f64656c2d636f6d706172652e6a706722, '2025-07-10', 'FAC-0002'),
(15, 'IP15PROMX', 'Iphone 16', 'Apple', 9, 5, 1000.00, 1000.00, 1000.00, 3, 0x6970686f6e652d31352d70726f2d6d6f64656c2d636f6d706172652e6a706722, '2025-07-12', 'FAC-0003'),
(16, 'IP14PROMAX1', 'Iphone14Promax', 'Apple', 11, 5, 1000.00, 1000.00, 1000.00, 1, 0x61313336346173642e6a706722, '2025-07-14', 'FAC-0004'),
(17, 'IP7', 'Iphone 7', 'Apple', 11, 5, 500.00, 500.00, 500.00, 1, 0x6970372e6a706722, '2025-07-16', 'FAC-0005'),
(18, 'IP15PROMX', 'Iphone 15 pro max', 'Apple', 11, 20, 1500.00, 1500.00, 1500.00, 4, 0x6970686f6e652d31352d70726f2d6d6f64656c2d636f6d706172652e6a706722, '2025-07-25', 'FAC-0006'),
(19, 'S23UT', 'S23 ULTRA', 'Samsung', 2, 15, 1200.00, 1200.00, 1200.00, 1, NULL, '2025-07-17', 'FAC-0007'),
(21, 'MI14PROMAX', 'Redmi 14 Pro', 'Xiaomi', 3, 10, 500.00, 500.00, 500.00, 1, 0xffd8ffe000104a46494600010101006000600000ffdb0043000a07070907060a0908090b0b0a0c0f19100f0e0e0f1e161712192420262523202322282d3930282a362b2223324432363b3d4040402630464b453e4a393f403dffdb0043010b0b0b0f0d0f1d10101d3d2923293d3d3d3d3d3d3d3d3d3d3d3d3d3d3d3d3d3d3d3d3d3d3d3d3d3d3d3d3d3d3d3d3d3d3d3d3d3d3d3d3d3d3d3d3d3d3d3d3d3dffc000110800b400b403012200021101031101ffc4001f0000010501010101010100000000000000000102030405060708090a0bffc400b5100002010303020403050504040000017d01020300041105122131410613516107227114328191a1082342b1c11552d1f02433627282090a161718191a25262728292a3435363738393a434445464748494a535455565758595a636465666768696a737475767778797a838485868788898a92939495969798999aa2a3a4a5a6a7a8a9aab2b3b4b5b6b7b8b9bac2c3c4c5c6c7c8c9cad2d3d4d5d6d7d8d9dae1e2e3e4e5e6e7e8e9eaf1f2f3f4f5f6f7f8f9faffc4001f0100030101010101010101010000000000000102030405060708090a0bffc400b51100020102040403040705040400010277000102031104052131061241510761711322328108144291a1b1c109233352f0156272d10a162434e125f11718191a262728292a35363738393a434445464748494a535455565758595a636465666768696a737475767778797a82838485868788898a92939495969798999aa2a3a4a5a6a7a8a9aab2b3b4b5b6b7b8b9bac2c3c4c5c6c7c8c9cad2d3d4d5d6d7d8d9dae2e3e4e5e6e7e8e9eaf2f3f4f5f6f7f8f9faffda000c03010002110311003f00f64a28a5a004ae26ebc47aa6ad7739d32e23b1b086468925f28492ceca70cc3770ab9e0752715dbd798e8b204d06d72718424ffdf4734d017dafb5a5ff0098f5c9ff00b7787ff89a89b53d713fe637747fedde1ffe26a86bb7b0dbc1e4c73b25c01999ba842704228eec01f989381d393d3cfeff0050f25cbc534c1ffbdb87f402803d1df5ed697fe635707fed843ffc4d40fe26d6d7fe6313ff00df98bff89ae2f45f14bdd4c2d6f1812fc472fbfa1ff1ad9964f7a606a378bb5c5ff98b4c7fed8c5ffc4d53bef1f6bb696eceba8cf23e40545823258938007cbd4922b2e4906e2b9e476ef4ba60597c41a50600817f01c1f66247f2149e8ae075b6cff1266815e49b4f819867cb9e405d7ebb6322a523e23ffcfee91ff7f0ff00f1baec334d2d5c1f5999d2a8a3903ff0b1bfe7f749ff00bf87ff008dd35bfe16471b6ff481f5627ff69d75e5a985aa5e2a652a1138b92efe21453c5049ac684934a098e269407703aed5d9938f6a7f9bf11c1f9b50d248f4048ffda75aba8f872c752d76cf56b8337da6d31b155f08c5492a587b166c608ebce6b459f14a58a96962e3864f7399375f10c7fcbee95ff7d9ff00e37541fc47e3c572a6eac32091c1ff00ec2bae77acfb0d3e1babb98dcb3ac31a976f2f1b8fcc001cfd6a562aab764692c2d38abb39ff00f8497c7839175604fa647ff115d2780fc7579ae5fdc68fae5aadbea56ebb8153c4838fc3a1ce4704669fa9695670da2dc5934bb77ec659083c9048c11f4ac4d06311fc50b22060b5a8c9f5f966ff000ade8d69ba9c9339ead282a7cf03d4e968a2bb0e5128a28a00296928a005af24b5b8fb3f87219319d899c7fc0ebd6fbd78f5a6d93468a27e55e32a47b1269a0383bed6a49a762ee4927249ee4f27f526a84dfe9085d5ced54cc9b97186e4f1ea318e7d7b5375dd36e6c2fe48e45cf24a301c3afa8ff3c565169e41e582fb4ff0e78a00b3a74adf6be33d335db6a5aa4b616893c44ac8d85de3aae476f7eb593a4f8705b5a8b9b83992e06235eeabfc4cde99e8075ef5ad7491dcc2d14ca191ba8a101c849a8cdf6849237264278033ebd3debaeb4bf5d3ef6d2f644675b7ba8246453c91b8e71f9d65dbe916b67319632ecd8c0de41c7e9534edbad9c7fd358bff004234a5b32a0af24bccf71b3beb7d46ce2bab4944b04a32ac3f91f423b8a94b578f786bc4d3f86ef0fde96ca53fbe847fe84bfed7f3e9e98f57b5bd82fed23b9b595658641b95d7bff9f4af2671713d49d270762c16a899e919aa277ac9b1c620ef50b3d23bd42cd50d9d11883b5476b732d95c196123710410c3208f7a6bb5285e2926d3ba0ad156b325bed46e2fd5566d8a8a73b517033eb58fa48c7c51d3ff00ebd7ff00659ab4f6d6769c31f14b4eff00af5fe93d75e164e55aece1c4251a5647a6d1494b5ea1e709451450014b4945002f7af17b47dba7c03fd9fea6bd86f24686ca792338748d994fa100d78a5ac85b4eb6e40668d793ea7ffd74d00ed4238e4b2692ea0325b86c6e65e377a03ebf4e6b9b960d3a07def61708b9eaced8fd6b7b53f120212284811409e5c00ffcb34ee47fb4c7927af38e82b92d4af9dc96dcd93df3401bd0cf04917fa315d83b018c7d698ef5cbe9978c97a194e3b301d1857479576f9a448d00cb3be70a3d78e6980c91ea12774127fd7587ff00423526a36f73a73446eed2e608e6ff0054f347b43fd2a18ce6193febac3ffa11a996ccba5fc48faa15d6b73c23addf693752ac31b5c58fde9e10c011d7e6404f2d853c0ea07d2b21d68b7beb8b0f37ecafb3cd003f19dc06783edcff002f4ae2b27a33e8aac5b5a1ecf149f69b749e10cf1ba8656da4641e9c1e698cae4e029ce09c7d39af36d23c7b7fa7de30bcdb2da4ad991553e64ce32cbf9648e879e95e849a80bb8229e1956489d49474e841ae5a94d40e58c677b0e68e439c231ebce3d066a0657f9be52368dc73c6054925e48d9dc54e7afca39ebfe26abcd73248ecee41665da78ed583e537846a7908f1c9b8ae064007a8e41e956444c235254818ee2a84d74edbce71b94290bc703b56aabb490aeec7201271f8ff005342e5e84d753495c876d65d90c7c54d3bfebd3fa4f5b5b6b9f795e0f8a3a418ce0b471a1e3b113e6bab08bf7a79f88fe19e9f4b494b5ea1e78945145002d1494b4015b51ff906dd7fd717ff00d04d78482c7478827def2411f8735eeda8ff00c836ebfeb8bffe826bc1e17c59c1ff005cd69a038dbbbc6f30fcdc1aab35eef88aec1b9bab13938f41e95d06a7a325c3b490e016e4a74e7dbfc2b1c6892893e647c7d28b00cd22de59ee249154ec8d0b31f4ec3f5addb912180088812210467a122ac09638b4f8ad6187ca00ef95b764cadd89e3803b01c55676a606d7887c6971e2bd2a3b3bcb04b5689c4accac5b7b818e33d0724f7ac7b7398a4ffaed0ffe846abbb54d66731c9ff5da0ffd08d4c95a2cd297f123ea8bee292cf4dbbd56f52cf4fb769ee1f242290381d4927803dea4714fb1d4aef48b97b8b094c33346d1798002429c6719e878eb5c3168fa6aa9d9f2ee773a1fc27b13bd35cbe33dd850cd6f6b26d11039c64fde39c1e781c1ae13c37e2597429cc336e92c646cba0e4c67fbcbfd477fad779f085de5bcd72595d9e47f24b3b1cb31fde7249eb5e52e3ad6938c6515a1e753e7f6938c9ded63dc6c4e8f269f0dddc5cb4de72ee5589b236e783c7f53568695a76ab03b69923a4c9d51c9fd41fe62bc8fc19a95f26ab0e956d135cc774c76c4180284024b29240e80e477fad7ae6976a74049eff53758b72ec48c3649e73f99ae754ed2e5715cbdff00e08ea3e54e519be6e8bfe01ccca4a960c0823823d2b7e05cdbc7fee0fe55cd5cdc19659656e0b92c40f7e6ba8b51fe8909ff00a66bfcab861bb3af189a8c6e2edae66ec63e28e8bfeec5ff00b715d562b96bd18f8a5a27fbb17fedc576e17f8879388f80f4fa5a4a5af4ce0128a28a00296928a006cbe5889fcedbe5ed3bb774c77cd7cfacc3ca4db8dbb78c7a57be6a3ff20dbaff00ae2fff00a09af9f0b7ee62ff0070534023bd40ef43bd40ed4c019ea176f7a566a859a80063566c39593febb41ffa11aa4c6ae69bcabffd7783ff00423532d99a51fe247d57e66c30a81d6acb0a89c579e8fac923bcf840313eb3feec3ffb3d796baf5aeefc0de28b2f0bc97ed7d1dc3fda04613c95071b77673923fbc2b8975ad9b5ca8f3a306ab4db5a3b105bdd5c58dcadcd9cf2413c79db244fb59723070474e0d7a47c31f15ea7aceab3e8babcad7f6cf6ed286986e64c10304f707777ee056569dacf83aef47b4b3f10e8f30b9b68962fb541d64006324a907f039ad05f1df867c2b61345e10d265fb54dd669c103d8b124b363d381f4ab8d975392b5e574a2effd7526d5a35b3d42ead95b2b1bb2ae7ae3b575d6633656ff00f5c97f90af21d1fc56b6b1c91ea624b917172f2cd231ced0db4920772486cfa678af61b174934cb464da43428415e87e51d2bce951e4937d0eaaf5b9e114f743f15ce3f943e2be95e76dc7d9576eefef627c63deba6c5725a971f15344ff00722ffdb8adb0bfc43ceaff0001e9b4b494b5e91c425145140052d25140115e46d3594f1c601778d9573ea41af9d24255514f50a01afa47bd7cd772dfbc34d0113b542ed4acd50b3530118d46c6863d6984d20109abda5f47ff00aef07fe846b3c9abfa57f1ff00d7683ff4234a5f0b35a3fc48faafccdf22a2715391c546c2bce3eba48aaeb55dd6ae38aaf22d526613894a45aaee2ae48b55dc55a39a68a8e3835e81e15f15b68922da5e167d3df1ee6027b8ff0067d47e23be78290706b6986547fba3f95534a4accc1c79ae99ede8eb222bc6cae8c032b29c8607a106b96be89e6f8ada2aa004ac31b9c9c703ed19ae6fc23e2d6d1241677cc5b4f63c3753013dc7fb3ea3f11df3d607593e2c692e8cac8d620ab29c82089f041ace8c1c2a9c3898b8c6ccf41a5a4a5aee3844a28a2800a5a4a5a002be66bb6c4cd5f4cd7cc578dfbf6fad34042cd51b350cd5193400134c26826984d0004d68691fc7ff005da0ff00d08d66935a3a3f57ff00aed07fe84694be166b47f891f55f99d29a8c8a94f02ac5c697796d11926876a83861b81653c1e47b646719c679c579c91f5f2925a3666b2d40eb5a70584b7681a3c16690428bce5d88ce3d071dce29dfd81a8b0cfd9f036963f3ae4607719e33d29a4cc67382d1b30a45aad22d6dbe8d74268e29152332890a313953b3ef72338e9d4f1ef4b2785efb6160d6ff0074b8dce5060123ab0007cc08e4f6cf420d68933967382ea73722f5ad8c7c8bf41fcaa1d5f479f4a11f9f242c64dd811b127838cf20707b1fad5b0998d7fdd1fcaa8ce367aa2ab2d749f0e66964f1dd94723b32451958c13f746c94e07b649fceb09a3addf876bb7e20db7fb87ff45cb5a53dce5c62fdd9edf45252d6c79225145140052d252d0015f2f5eb7fa4bfd6bea1af96ef8ffa549f5a680809a6134134d26800269a4d04d34f3400135a3a37de7ffaed07fe846b34d68e8df7dffebb41ff00a11a52f859ad1fe2c7d57e67590c9e45c452ed0de5babed3df0738adebcd76d9b4cf261e5fcb28a0458ea082189cf00118e49e3b0ae7e9a4579f1935b1f5b52846a34e5d0b563123472336a3f6421d40504e5b8273c7a74fc6965b6855e211eaecc2470aeff77cb05792467df1edce6aa2c324a18c71bb85fbc554903ebf9540cc31f787e7427a112a776ddff2348d9d990cdfdb6ea04651017e572149cfb12586defc77a86e6cac6e3e45d6f6a1fbde6b96078233cfd00c7a77e82b35c8f51f9d457313c014cc8f1075dcbbd4aee1ea33d47bd5a9791cf2a2eff17e45ab9d2b4f9b2ebadae483c4c3240007539ebc9c0c7b7506a69218d1cac3219235e15c8c6e03a1c7bd624ea519d1c1575e0a9e08fc2ba48ed2616f1b18650a54004a1c640e7f91fcaaae6718f2eeca463ad7f00aedf88769ff005ccffe812d53684ab10ca411d4115a3e085dbf11acff00eb91ff00d025aba7f11cd8d5fba67b2d2d252d741e289451450014b494b40057cb17c7fd2e4fad7d4f5f2b5f1ff4b93eb4d010134d2682693340084d21341a4a00335a5a29f9dffebb41ff00a11acc26b4b45ff58dff005da1ff00d08d297c2cd68ff163eabf33b0b5111ba87ed07f73b86ffa56bdc8d04c170d16f56c62355c939c9c11f86debefed58741af394add0faea94b9da776bd0bfa6fdb443bece1590c5296539c32bedc71c8ce411c73d2af4771a92bc0c6ce19220198885b6eec6ee0b67d49e9eb81deb0c48e8a555dd41e480c40a56bcba39ff00499f9393fbc6e4faf5f7354a5632a945c9bd89a6d7ae65471e5dbab3a6cdea982a36e3039e3fcf6e2aa43abcf6b0948e384e2311ab329ca8008e39ebcd44c2a161429313a304ad62c5df886ee7501920055d240c149394208ea79e40fa0e060715bd06a9723132ac6a5d72542f1924127af5e319f4cfad72322f06ba8813fd1e3ff717f955733315461d864bba695e47e59d8b1fa9ab3e105dbf122c47fd313ffa04b4dd95378546df895603fe981ffd026ad293f78e6c7ab507f23d76968a2ba4f044a28a2800a28a5a002be55bd3fe972ffbc6beaaaf94efbfe3f25ff78d340404d368a4a00290d0690d0015a7a27fac6ffaed0ffe846b32b4b44ff5adff005da1ff00d08d297c2cd68ff163eabf33ab6242923b0ad4bd82ca359e1b78cf990282d26e3d7705c7279ebc9acc23208ec455996f04be7110aac937fac70cc73c83c03c0e40af3933ebea45b69a1d6d1da3d949e7c8126327cb9273b429c01c1032dc13cd39ed34d585dc5fc8cc036d5d9d481f293c719fea3de9b65770da2967b55965c92acc78008c608c73ff00d734f7bfb56cb0d3a2dfe62b82481c03920800673f974e29ab58ca4a7cced7b7c845874711a192e277628a1b67003e1b71e47ddcedc639e3d0f0d6b6d20c89fbf01013b86f7e46d6dbce339cecddd31938e2a76d5ad5e6334da7abcae41762e39c104718c76ef91c0aced42e22ba955e1b64838f9829cee39273fad55d232509c9eb75f3432ea1d355a208e594c4fbf6bb70db3e5c923aefc820718c73d6bab860d385b47ba539d8bcc659bb0ce338e3ebcd71122f06baa807fa3c5fee2ff2a3981d27fccc9ae2185241e43ef52324fa1f4e951f86863e26587fd703ff00a04d4b4be1cff929ba7ffd7b9ffd026ad293f7ce5cc15b0efe47acd2d145751f3e25145140051452d0015f295fff00c7ecbfef1afab6be58d72d25b0d66eedae14ac914ac8c0fa834d014290d2e47ad373ef4005252d2668016b4b45ff005c7febac3ffa11acdcd5ab2ba168b2c98dc55a39368ee15b27f434a5aa65d26a338b7d1a3b4a2a8c7ade992a0617b1007b39da47e14ffed7d3bfe7fadffefbaf3b965d8fb155a9357525f7a2dd2555fed7d3bfe7fadffefba4fed7d3bfe7fadffefba395f60f6b4ff997de8b245308a80eada77fcff5bffdf74d3aae9fff003fd6ff00f7dd3e57d897569ff32fbd1238e0d7510ffa88ff00dc1fcab8f6d52c083fe9b07fdf75d045afe902140753b40428e3ccf6a6a2fb194aad3fe65f79a54be1cff929ba7ffd7b9ffd026ace3e21d1c024ea96981e8f9a9bc017cbe20f8906eac559ad2ce03990ae33f2b2e7db25ce07a2d6b462f9ae79f985583a3ca9ea7b252d252d751e1094514500145145002d626b3e0fd0fc41309753d3e3966031e6ab14723d095209fc68a28032bfe1557853fe81f27fe04c9ffc551ff0aabc29ff0040f93ff0264ffe2a8a29807fc2aaf0a7fd03a4ff00c0993ff8aa3fe155784ffe81d27fe04cbffc55145001ff000aa7c27ff40e93ff000265ff00e2a81f0abc280e469f2023b8ba97ff008aa28a0078f85fe175181632fe37329ffd9a97fe158785ff00e7c64ffc0993ff008aa28a003fe158f85ffe7c24ff00c0993ff8aa3fe158f85ffe7c24ff00c0993ff8aa28a003fe158f85ff00e7c24ffc0993ff008aa3fe158f85ff00e7c24ffc0993ff008aa28a003fe158f85ffe7c24ff00c0993ff8aa3fe158f85ffe7c24ff00c0993ff8aa28a003fe158f85ff00e7c24ffc0997ff008aaddd2745d3b43b636fa5da456d11396083963ea4f527eb451480bd4b451400945145007fffd9, '2025-07-30', 'FAC-0008'),
(22, 'MI14PROMAX', 'Redmi 14 Pro', 'Apple', 3, 3, 600.00, 600.00, 600.00, 2, NULL, '2025-07-24', 'FAC-0009'),
(23, 'P50', 'Huawei p50', 'Huawei', 12, 0, 50.00, 50.00, 50.00, 1, NULL, '2025-07-01', 'FAC-0010'),
(24, 'P50', 'Huawei p50', 'Huawei', 1, 7, 60.00, 60.00, 60.00, 2, NULL, '2025-07-05', 'FAC-0011'),
(25, 'P0', 'P0', 'Apple', 1, 15, 10.00, 10.00, 10.00, 1, NULL, '2025-07-01', 'FAC-0012'),
(26, 'P0', 'P0', 'Apple', 1, 20, 20.00, 20.00, 20.00, 2, NULL, '2025-07-04', 'FAC-0013'),
(27, 'TEST01', 'Producto Test', 'MarcaX', 1, 0, 50.00, NULL, NULL, NULL, NULL, NULL, NULL),
(28, 'TEST01', 'Artículo X', '', 1, 10, 0.00, 45.00, NULL, 1, NULL, '2025-07-01', 'FAC-0001'),
(29, 'TEST01', 'Artículo X', '', 1, 5, 0.00, 47.00, NULL, 2, NULL, '2025-07-05', 'FAC-0002'),
(30, 'PROD001', 'Producto Prueba', 'Xiaomi', 1, 6, 100.00, 100.00, 100.00, 1, NULL, '2025-07-01', 'FAC-0014'),
(31, 'PRUEBA0156', 'Producto Prueba0156', 'Apple', 1, 0, 50.00, 50.00, 50.00, 1, NULL, '2025-07-01', 'FAC-0015'),
(32, 'PRUEBA0156', 'Producto Prueba0156', 'Apple', 1, 2, 70.00, 70.00, 70.00, 2, NULL, '2025-07-03', 'FAC-0016');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `proveedor`
--

CREATE TABLE `proveedor` (
  `id` int(11) NOT NULL,
  `ruc` varchar(15) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `telefono` varchar(15) NOT NULL,
  `direccion` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `proveedor`
--

INSERT INTO `proveedor` (`id`, `ruc`, `nombre`, `telefono`, `direccion`) VALUES
(1, '20123456781', 'AOC PERU', '798978879', 'Lima - Perú'),
(2, '20300263578', 'SAMSUNG PERU S.A.C', '711 4800 ', 'Av. Rivera Navarrete 501 Piso 6'),
(3, '20111222337', 'XIAOMI PERU', '711 6471', 'Av.28 de Julio, La victoria'),
(4, '20055168446', 'PHILIPS ', '411 6497', 'San Isidro'),
(5, '20122334458', 'IMPORTACIONES PEGASO', '976646411', 'Canevaro, Lince'),
(6, '20155667781', 'UTP', '799 1164', 'Av. 28 de Julio - La Victoria'),
(7, '20166778892', 'IMPORTACIONES SUPER', '955664471', 'Av. Abancay 2411- Cercado de Lima'),
(8, '20177889903', 'LENOVO', '461 1644', 'Av.Larco- MIraflores'),
(9, '20188990014', 'MAC CENTER PERU', '441 1616', 'Av. Arequipa 325- San Isidro'),
(10, '2050000641', 'E&S IMPORTACIONES', '466 794', 'Av.El Bosque-San Juan de Lurigancho'),
(11, '2070064516', 'ISHOP', '01 646 4741', 'Av. Ricardo Rivera Navarrete Nro. 475 Int. 1004'),
(12, '200599746216', 'HUAWEI PERU', '9771164641', 'Los Olivos'),
(13, '109974616', 'PACHECO MARCOS MIGUEL ALEXANDER', '974616466', 'Av. Santa Rosa');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id` int(11) NOT NULL,
  `nombre` varchar(200) NOT NULL,
  `correo` varchar(200) NOT NULL,
  `pass` varchar(50) NOT NULL,
  `rol` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `nombre`, `correo`, `pass`, `rol`) VALUES
(1, 'MIGUEL PACHECO', '75423906', 'admin', 'Administrador'),
(2, 'Jean Calderon', '2004', 'admin', 'Administrador'),
(4, 'VENDEDOR', '123456', '123', 'Asistente');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ventas`
--

CREATE TABLE `ventas` (
  `id` int(11) NOT NULL,
  `cliente` int(11) NOT NULL,
  `vendedor` varchar(60) NOT NULL,
  `total` decimal(10,2) NOT NULL,
  `fecha` date DEFAULT NULL,
  `numero_comprobante` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `ventas`
--

INSERT INTO `ventas` (`id`, `cliente`, `vendedor`, `total`, `fecha`, `numero_comprobante`) VALUES
(4, 1, 'Jean Carlos', 15000.00, '0000-00-00', NULL),
(5, 1, 'MIGUEL PACHECO', 1800.00, '0000-00-00', NULL),
(6, 1, 'MIGUEL PACHECO', 9000.00, '0000-00-00', NULL),
(7, 1, 'MIGUEL PACHECO', 9000.00, '0000-00-00', NULL),
(8, 1, 'MIGUEL PACHECO', 3000.00, '0000-00-00', NULL),
(9, 1, 'MIGUEL PACHECO', 3000.00, '0000-00-00', NULL),
(10, 1, 'MIGUEL PACHECO', 2500.00, '0000-00-00', NULL),
(11, 1, 'MIGUEL PACHECO', 7500.00, '0000-00-00', NULL),
(12, 5, 'MIGUEL PACHECO', 4500.00, '0000-00-00', NULL),
(13, 5, 'MIGUEL PACHECO', 13500.00, '0000-00-00', NULL),
(14, 5, 'MIGUEL PACHECO', 3000.00, '0000-00-00', NULL),
(15, 1, 'MIGUEL PACHECO', 22500.00, '0000-00-00', NULL),
(16, 1, 'MIGUEL PACHECO', 400.00, '0000-00-00', NULL),
(17, 1, 'MIGUEL PACHECO', 6000.00, '2025-07-15', 'BOLETA-17'),
(18, 1, 'MIGUEL PACHECO', 400.00, '2025-07-15', 'BOLETA-18'),
(19, 1, 'MIGUEL PACHECO', 750.00, '2025-07-15', 'BOLETA-19'),
(20, 1, 'usuario', 300.00, '2025-07-10', NULL),
(21, 1, 'admin', 300.00, '2025-07-10', NULL),
(22, 1, 'MIGUEL PACHECO', 400.00, '2025-07-16', 'BOLETA-22'),
(23, 1, 'MIGUEL PACHECO', 650.00, '2025-07-16', 'BOLETA-23');

-- --------------------------------------------------------

--
-- Estructura Stand-in para la vista `vw_kardex`
-- (Véase abajo para la vista actual)
--
CREATE TABLE `vw_kardex` (
`fecha` date
,`codigo_producto` varchar(50)
,`E_Cant` int(11)
,`E_CU` decimal(10,2)
,`E_CT` decimal(10,2)
,`S_Cant` int(11)
,`S_CU` decimal(10,2)
,`S_CT` decimal(10,2)
,`D_Cant` int(11)
,`D_CU` decimal(10,2)
,`D_CT` decimal(10,2)
);

-- --------------------------------------------------------

--
-- Estructura para la vista `vw_kardex`
--
DROP TABLE IF EXISTS `vw_kardex`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vw_kardex`  AS SELECT `k`.`fecha` AS `fecha`, `k`.`codigo_producto` AS `codigo_producto`, `k`.`e_cantidad` AS `E_Cant`, `k`.`e_cu` AS `E_CU`, `k`.`e_ct` AS `E_CT`, `k`.`s_cantidad` AS `S_Cant`, `k`.`s_cu` AS `S_CU`, `k`.`s_ct` AS `S_CT`, `k`.`d_cantidad` AS `D_Cant`, `k`.`d_cu` AS `D_CU`, `k`.`d_ct` AS `D_CT` FROM `kardex` AS `k` ORDER BY `k`.`fecha` ASC, `k`.`id` ASC ;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `compras`
--
ALTER TABLE `compras`
  ADD PRIMARY KEY (`id`),
  ADD KEY `producto_id` (`producto_id`);

--
-- Indices de la tabla `config`
--
ALTER TABLE `config`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `detalle`
--
ALTER TABLE `detalle`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_venta` (`id_venta`),
  ADD KEY `id_pro` (`id_pro`);

--
-- Indices de la tabla `kardex`
--
ALTER TABLE `kardex`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `productos`
--
ALTER TABLE `productos`
  ADD PRIMARY KEY (`id`),
  ADD KEY `proveedor` (`proveedor`);

--
-- Indices de la tabla `proveedor`
--
ALTER TABLE `proveedor`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `ventas`
--
ALTER TABLE `ventas`
  ADD PRIMARY KEY (`id`),
  ADD KEY `cliente` (`cliente`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `clientes`
--
ALTER TABLE `clientes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `compras`
--
ALTER TABLE `compras`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `config`
--
ALTER TABLE `config`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `detalle`
--
ALTER TABLE `detalle`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT de la tabla `kardex`
--
ALTER TABLE `kardex`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `productos`
--
ALTER TABLE `productos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

--
-- AUTO_INCREMENT de la tabla `proveedor`
--
ALTER TABLE `proveedor`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `ventas`
--
ALTER TABLE `ventas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `compras`
--
ALTER TABLE `compras`
  ADD CONSTRAINT `compras_ibfk_1` FOREIGN KEY (`producto_id`) REFERENCES `productos` (`id`);

--
-- Filtros para la tabla `detalle`
--
ALTER TABLE `detalle`
  ADD CONSTRAINT `detalle_ibfk_1` FOREIGN KEY (`id_pro`) REFERENCES `productos` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `detalle_ibfk_2` FOREIGN KEY (`id_venta`) REFERENCES `ventas` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `productos`
--
ALTER TABLE `productos`
  ADD CONSTRAINT `productos_ibfk_1` FOREIGN KEY (`proveedor`) REFERENCES `proveedor` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `ventas`
--
ALTER TABLE `ventas`
  ADD CONSTRAINT `ventas_ibfk_1` FOREIGN KEY (`cliente`) REFERENCES `clientes` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
