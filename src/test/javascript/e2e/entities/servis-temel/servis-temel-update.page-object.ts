import { element, by, ElementFinder } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

import path from 'path';

const expect = chai.expect;

const fileToUpload = '../../../../../../src/main/webapp/content/images/logo-jhipster.png';
const absolutePath = path.resolve(__dirname, fileToUpload);
export default class ServisTemelUpdatePage {
  pageTitle: ElementFinder = element(by.id('servisTakipApp.servisTemel.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  servisAdiInput: ElementFinder = element(by.css('input#servis-temel-servisAdi'));
  servisUrlInput: ElementFinder = element(by.css('input#servis-temel-servisUrl'));
  servisVeriTipInput: ElementFinder = element(by.css('input#servis-temel-servisVeriTip'));
  aciklamasiInput: ElementFinder = element(by.css('input#servis-temel-aciklamasi'));
  dosyasiInput: ElementFinder = element(by.css('input#servis-temel-dosyasi'));
  resmiInput: ElementFinder = element(by.css('input#servis-temel-resmi'));
  servisSekliSelect: ElementFinder = element(by.css('select#servis-temel-servisSekli'));
  servisTipiSelect: ElementFinder = element(by.css('select#servis-temel-servisTipi'));
  baglantiSekliSelect: ElementFinder = element(by.css('select#servis-temel-baglantiSekli'));
  servisDurumSelect: ElementFinder = element(by.css('select#servis-temel-servisDurum'));
  anlikSorguInput: ElementFinder = element(by.css('input#servis-temel-anlikSorgu'));
  yiginVeriInput: ElementFinder = element(by.css('input#servis-temel-yiginVeri'));
  gdsInput: ElementFinder = element(by.css('input#servis-temel-gds'));
  protokolSelect: ElementFinder = element(by.css('select#servis-temel-protokol'));
  bakanlikSelect: ElementFinder = element(by.css('select#servis-temel-bakanlik'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setServisAdiInput(servisAdi) {
    await this.servisAdiInput.sendKeys(servisAdi);
  }

  async getServisAdiInput() {
    return this.servisAdiInput.getAttribute('value');
  }

  async setServisUrlInput(servisUrl) {
    await this.servisUrlInput.sendKeys(servisUrl);
  }

  async getServisUrlInput() {
    return this.servisUrlInput.getAttribute('value');
  }

  async setServisVeriTipInput(servisVeriTip) {
    await this.servisVeriTipInput.sendKeys(servisVeriTip);
  }

  async getServisVeriTipInput() {
    return this.servisVeriTipInput.getAttribute('value');
  }

  async setAciklamasiInput(aciklamasi) {
    await this.aciklamasiInput.sendKeys(aciklamasi);
  }

  async getAciklamasiInput() {
    return this.aciklamasiInput.getAttribute('value');
  }

  async setDosyasiInput(dosyasi) {
    await this.dosyasiInput.sendKeys(dosyasi);
  }

  async getDosyasiInput() {
    return this.dosyasiInput.getAttribute('value');
  }

  async setResmiInput(resmi) {
    await this.resmiInput.sendKeys(resmi);
  }

  async getResmiInput() {
    return this.resmiInput.getAttribute('value');
  }

  async setServisSekliSelect(servisSekli) {
    await this.servisSekliSelect.sendKeys(servisSekli);
  }

  async getServisSekliSelect() {
    return this.servisSekliSelect.element(by.css('option:checked')).getText();
  }

  async servisSekliSelectLastOption() {
    await this.servisSekliSelect.all(by.tagName('option')).last().click();
  }
  async setServisTipiSelect(servisTipi) {
    await this.servisTipiSelect.sendKeys(servisTipi);
  }

  async getServisTipiSelect() {
    return this.servisTipiSelect.element(by.css('option:checked')).getText();
  }

  async servisTipiSelectLastOption() {
    await this.servisTipiSelect.all(by.tagName('option')).last().click();
  }
  async setBaglantiSekliSelect(baglantiSekli) {
    await this.baglantiSekliSelect.sendKeys(baglantiSekli);
  }

  async getBaglantiSekliSelect() {
    return this.baglantiSekliSelect.element(by.css('option:checked')).getText();
  }

  async baglantiSekliSelectLastOption() {
    await this.baglantiSekliSelect.all(by.tagName('option')).last().click();
  }
  async setServisDurumSelect(servisDurum) {
    await this.servisDurumSelect.sendKeys(servisDurum);
  }

  async getServisDurumSelect() {
    return this.servisDurumSelect.element(by.css('option:checked')).getText();
  }

  async servisDurumSelectLastOption() {
    await this.servisDurumSelect.all(by.tagName('option')).last().click();
  }
  async setAnlikSorguInput(anlikSorgu) {
    await this.anlikSorguInput.sendKeys(anlikSorgu);
  }

  async getAnlikSorguInput() {
    return this.anlikSorguInput.getAttribute('value');
  }

  async setYiginVeriInput(yiginVeri) {
    await this.yiginVeriInput.sendKeys(yiginVeri);
  }

  async getYiginVeriInput() {
    return this.yiginVeriInput.getAttribute('value');
  }

  async setGdsInput(gds) {
    await this.gdsInput.sendKeys(gds);
  }

  async getGdsInput() {
    return this.gdsInput.getAttribute('value');
  }

  async protokolSelectLastOption() {
    await this.protokolSelect.all(by.tagName('option')).last().click();
  }

  async protokolSelectOption(option) {
    await this.protokolSelect.sendKeys(option);
  }

  getProtokolSelect() {
    return this.protokolSelect;
  }

  async getProtokolSelectedOption() {
    return this.protokolSelect.element(by.css('option:checked')).getText();
  }

  async bakanlikSelectLastOption() {
    await this.bakanlikSelect.all(by.tagName('option')).last().click();
  }

  async bakanlikSelectOption(option) {
    await this.bakanlikSelect.sendKeys(option);
  }

  getBakanlikSelect() {
    return this.bakanlikSelect;
  }

  async getBakanlikSelectedOption() {
    return this.bakanlikSelect.element(by.css('option:checked')).getText();
  }

  async save() {
    await this.saveButton.click();
  }

  async cancel() {
    await this.cancelButton.click();
  }

  getSaveButton() {
    return this.saveButton;
  }

  async enterData() {
    await waitUntilDisplayed(this.saveButton);
    await this.setServisAdiInput('servisAdi');
    await waitUntilDisplayed(this.saveButton);
    await this.setServisUrlInput('servisUrl');
    await waitUntilDisplayed(this.saveButton);
    await this.setServisVeriTipInput('servisVeriTip');
    await waitUntilDisplayed(this.saveButton);
    await this.setAciklamasiInput('aciklamasi');
    await waitUntilDisplayed(this.saveButton);
    await this.setDosyasiInput(absolutePath);
    await waitUntilDisplayed(this.saveButton);
    await this.setResmiInput(absolutePath);
    await waitUntilDisplayed(this.saveButton);
    await this.servisSekliSelectLastOption();
    await waitUntilDisplayed(this.saveButton);
    await this.servisTipiSelectLastOption();
    await waitUntilDisplayed(this.saveButton);
    await this.baglantiSekliSelectLastOption();
    await waitUntilDisplayed(this.saveButton);
    await this.servisDurumSelectLastOption();
    await waitUntilDisplayed(this.saveButton);
    await this.setAnlikSorguInput('anlikSorgu');
    await waitUntilDisplayed(this.saveButton);
    await this.setYiginVeriInput('yiginVeri');
    await waitUntilDisplayed(this.saveButton);
    await this.setGdsInput('gds');
    await this.protokolSelectLastOption();
    await this.bakanlikSelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
  }
}
