import { element, by, ElementFinder } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class SorunTemelUpdatePage {
  pageTitle: ElementFinder = element(by.id('servisTakipApp.sorunTemel.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  sorunAciklamasiInput: ElementFinder = element(by.css('input#sorun-temel-sorunAciklamasi'));
  sorunTarihiInput: ElementFinder = element(by.css('input#sorun-temel-sorunTarihi'));
  cozumAciklamasiInput: ElementFinder = element(by.css('input#sorun-temel-cozumAciklamasi'));
  cozumTarihiInput: ElementFinder = element(by.css('input#sorun-temel-cozumTarihi'));
  servisSelect: ElementFinder = element(by.css('select#sorun-temel-servis'));
  bulanSelect: ElementFinder = element(by.css('select#sorun-temel-bulan'));
  cozenSelect: ElementFinder = element(by.css('select#sorun-temel-cozen'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setSorunAciklamasiInput(sorunAciklamasi) {
    await this.sorunAciklamasiInput.sendKeys(sorunAciklamasi);
  }

  async getSorunAciklamasiInput() {
    return this.sorunAciklamasiInput.getAttribute('value');
  }

  async setSorunTarihiInput(sorunTarihi) {
    await this.sorunTarihiInput.sendKeys(sorunTarihi);
  }

  async getSorunTarihiInput() {
    return this.sorunTarihiInput.getAttribute('value');
  }

  async setCozumAciklamasiInput(cozumAciklamasi) {
    await this.cozumAciklamasiInput.sendKeys(cozumAciklamasi);
  }

  async getCozumAciklamasiInput() {
    return this.cozumAciklamasiInput.getAttribute('value');
  }

  async setCozumTarihiInput(cozumTarihi) {
    await this.cozumTarihiInput.sendKeys(cozumTarihi);
  }

  async getCozumTarihiInput() {
    return this.cozumTarihiInput.getAttribute('value');
  }

  async servisSelectLastOption() {
    await this.servisSelect.all(by.tagName('option')).last().click();
  }

  async servisSelectOption(option) {
    await this.servisSelect.sendKeys(option);
  }

  getServisSelect() {
    return this.servisSelect;
  }

  async getServisSelectedOption() {
    return this.servisSelect.element(by.css('option:checked')).getText();
  }

  async bulanSelectLastOption() {
    await this.bulanSelect.all(by.tagName('option')).last().click();
  }

  async bulanSelectOption(option) {
    await this.bulanSelect.sendKeys(option);
  }

  getBulanSelect() {
    return this.bulanSelect;
  }

  async getBulanSelectedOption() {
    return this.bulanSelect.element(by.css('option:checked')).getText();
  }

  async cozenSelectLastOption() {
    await this.cozenSelect.all(by.tagName('option')).last().click();
  }

  async cozenSelectOption(option) {
    await this.cozenSelect.sendKeys(option);
  }

  getCozenSelect() {
    return this.cozenSelect;
  }

  async getCozenSelectedOption() {
    return this.cozenSelect.element(by.css('option:checked')).getText();
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
    await this.setSorunAciklamasiInput('sorunAciklamasi');
    await waitUntilDisplayed(this.saveButton);
    await this.setSorunTarihiInput('01-01-2001');
    await waitUntilDisplayed(this.saveButton);
    await this.setCozumAciklamasiInput('cozumAciklamasi');
    await waitUntilDisplayed(this.saveButton);
    await this.setCozumTarihiInput('01-01-2001');
    await this.servisSelectLastOption();
    await this.bulanSelectLastOption();
    await this.cozenSelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
  }
}
