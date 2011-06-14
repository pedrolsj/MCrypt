using System;
using System.Text;
using System.Collections.Generic;
using System.Configuration;
using System.Security.Cryptography;
namespace Br.Com.Monty.CSharp.Crypt
{
    /// <summary>
    /// MCrypt class
    /// </summary>
    public static class MCrypt
    {
        #region Methods

        /// <summary>
        /// Creates the RSA.
        /// </summary>
        /// <returns></returns>
        private static RSACryptoServiceProvider CreateRSA()
        {
            CspParameters param = new CspParameters();
            param.Flags = CspProviderFlags.UseMachineKeyStore;

            RSACryptoServiceProvider rsa = new RSACryptoServiceProvider(param);
            return rsa;
        }

        /// <summary>
        /// Generates the RSA keys.
        /// </summary>
        /// <returns></returns>
        public static string GenerateRSAKeys()
        {
            return CreateRSA().ToXmlString(true);
        }

        /// <summary>
        /// Gets the RSA public key.
        /// </summary>
        /// <param name="keys">The keys.</param>
        /// <returns></returns>
        public static string GetRSAPublicKey(string keys)
        {
            RSACryptoServiceProvider rsa = CreateRSA();
            rsa.FromXmlString(keys);

            return rsa.ToXmlString(false);
        }

        /// <summary>
        /// Encrypts with RSA.
        /// </summary>
        /// <param name="publicKey">The public key.</param>
        /// <param name="clearText">The clear text.</param>
        /// <returns></returns>
        public static string EncryptRSA(string publicKey, string clearText)
        {
            RSACryptoServiceProvider rsa = CreateRSA(); 
            rsa.FromXmlString(publicKey);
            int maxSize = 80;
            StringBuilder cypherText = new StringBuilder();
            while (!String.IsNullOrEmpty(clearText))
            {
                cypherText.AppendFormat("{0}\n", Convert.ToBase64String(rsa.Encrypt(Encoding.Default.GetBytes(clearText.Length > maxSize ? clearText.Substring(0, maxSize) : clearText), true)));
                clearText = clearText.Length > maxSize ? clearText.Substring(maxSize) : String.Empty;
            }

            return cypherText.ToString();
        }

        /// <summary>
        /// Encrypts the RSA to java.
        /// </summary>
        /// <param name="publicKey">The public key.</param>
        /// <param name="clearText">The clear text.</param>
        /// <returns></returns>
        public static string EncryptRSAToJava(string publicKey, string clearText)
        {
            RSACryptoServiceProvider rsa = CreateRSA();
            rsa.FromXmlString(publicKey);
            int maxSize = 80;
            StringBuilder cypherText = new StringBuilder();
            while (!String.IsNullOrEmpty(clearText))
            {
                cypherText.AppendFormat("{0}\n", Convert.ToBase64String(rsa.Encrypt(Encoding.Default.GetBytes(clearText.Length > maxSize ? clearText.Substring(0, maxSize) : clearText), false)));
                clearText = clearText.Length > maxSize ? clearText.Substring(maxSize) : String.Empty;
            }

            return cypherText.ToString();
        }

        /// <summary>
        /// Decrypts with RSA.
        /// </summary>
        /// <param name="privateKey">The private key.</param>
        /// <param name="cypherText">The cypher text.</param>
        /// <returns></returns>
        public static string DecryptRSA(string privateKey, string cypherText)
        {
            RSACryptoServiceProvider rsa = CreateRSA();
            rsa.FromXmlString(privateKey);

            string clearText = String.Empty;
            foreach (string line in cypherText.Split('\n'))
                if (!String.IsNullOrEmpty(line))
                    clearText += Encoding.Default.GetString(rsa.Decrypt(Convert.FromBase64String(line), true));

            return clearText;
        }

        /// <summary>
        /// Decrypts the RSA from java.
        /// </summary>
        /// <param name="privateKey">The private key.</param>
        /// <param name="cypherText">The cypher text.</param>
        /// <returns></returns>
        public static string DecryptRSAFromJava(string privateKey, string cypherText)
        {
            RSACryptoServiceProvider rsa = CreateRSA();
            rsa.FromXmlString(privateKey);

            string clearText = String.Empty;
            foreach (string line in cypherText.Split('\n'))
                if (!String.IsNullOrEmpty(line))
                    clearText += Encoding.Default.GetString(rsa.Decrypt(Convert.FromBase64String(line), false));

            return clearText;
        }

        #endregion
    }
}
