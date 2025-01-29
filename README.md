# 🏋️ MightyMotion
Autor: Pere Garcias Ferrer

## CRITERIOS

- Look and Feel:
  Se utiliza FlatLightLaf y se añade un jLabel con un icono para que al pulsarlo haga switch a "DarkMode"(FlatDarkLaf) y "LightMode".
- Imagenes e iconos:
  - Se utiliza el componente BlobTracker (https://github.com/Perelluent/BlobTracker.git) para detectar nuevas imágenes en el blob de Azure.
  - Se crea la carpeta /resources/images donde se alojan los iconos que se utilizan en la aplicación, tales como flechas, interruptor (cerrar sesión), hamburguesa menú y DarkMode.
- Fuente y texto:
  - La fuente utilizada es "Carlito" para toda la aplicación y "Modern M" para la marca Mighty Motion.
  - Las excepciones y mensajes de error son marcados en un jLabel de fuente color rojo.
- Distribuir y agrupar componentes:
  - Los componentes se agrupan en un JFrame para la pantalla de inicio y JPanels para las demás pantallas.
- Redefinir los layouts:
  - Se rehace todo el código para combinar varios Layouts. MigLayout para la colocación de los componentes gráficos.
    CardLayout para gestionar el cambio de "pantallas" y BorderLayout para gestionar el menú izquierdo.
- Gestión de errores y mensajes al usuario:
  - Se implanta una regular expression para verificar que el e-mail sea válido cuando se da de alta un usuario y que todos los campos no sean null.
    También se gestiona que el usuario esté en la base de datos. En caso contrario, un JLabel de fuente de color rojo indica el error para que se pueda subsanar.
- Affordance:
  - Todos los botones que son clickables se modifica el puntero del ratón a Hand_Cursor para que se vea que es clickable.

## SKETCH INICIAL: 
![Sketch DI01](https://github.com/user-attachments/assets/588d51e4-3524-4390-bdfb-ca897edf1755)


## DEMO FINAL
![image](https://github.com/user-attachments/assets/10adc849-5c42-4ec2-b462-773ab08065a1)
![MM2](https://github.com/user-attachments/assets/93ad6e75-4b50-43c4-a281-7b577647d208)
![MM3](https://github.com/user-attachments/assets/348d6ee3-2185-4a95-bca1-9e89ae2b2012)
![image](https://github.com/user-attachments/assets/50a799e6-3f76-4e69-bcf8-ab764a8bae11)
![image](https://github.com/user-attachments/assets/08daa08b-4e30-4864-8514-f7410f99a958)
![image](https://github.com/user-attachments/assets/3ec096c9-774c-47d7-9772-a5a5c9a398b5)








